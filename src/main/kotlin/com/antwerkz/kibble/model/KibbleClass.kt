package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import com.antwerkz.kibble.model.KibbleExtractor.extractAnnotations
import com.antwerkz.kibble.model.KibbleExtractor.extractClasses
import com.antwerkz.kibble.model.KibbleExtractor.extractFunctions
import com.antwerkz.kibble.model.KibbleExtractor.extractObjects
import com.antwerkz.kibble.model.KibbleExtractor.extractProperties
import com.antwerkz.kibble.model.KibbleExtractor.extractSuperCallArgs
import com.antwerkz.kibble.model.KibbleExtractor.extractSuperType
import com.antwerkz.kibble.model.KibbleExtractor.extractSuperTypes
import com.antwerkz.kibble.model.Modality.FINAL
import com.antwerkz.kibble.model.Visibility.PUBLIC
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.psiUtil.modalityModifier
import org.jetbrains.kotlin.psi.psiUtil.visibilityModifier

/**
 * Represents an annotation in kotlin source code.
 *
 * @property name the class name
 * @property initBlock any custom init block for this class
 * @property constructor the primary constructor for this class
 * @property secondaries the secondary constructors this class
 */
class KibbleClass internal constructor(override var file: KibbleFile,
                                       var name: String = "",
                                       override var modality: Modality = FINAL,
                                       override var visibility: Visibility = PUBLIC) : KibbleElement, FunctionHolder, GenericCapable,
        Visible, Modal<KibbleClass>, AnnotationHolder, PropertyHolder, ClassOrObjectHolder {

    private var kt: KtClass? = null
    val superTypes: MutableList<KibbleType> by lazy {
        extractSuperTypes(file, kt?.superTypeListEntries)
    }

    var superType: KibbleType? = null
        private set

    var superCallArgs = listOf<String>()
        private set

    override var typeParameters = mutableListOf<TypeParameter>()

    override val annotations: MutableList<KibbleAnnotation> by lazy {
        extractAnnotations(file, kt?.annotationEntries)
    }

    override val classes: MutableList<KibbleClass> by lazy {
        extractClasses(kt?.declarations, file)
    }

    override val objects: MutableList<KibbleObject> by lazy {
        extractObjects(file, kt?.declarations)
    }

    override val functions: MutableList<KibbleFunction> by lazy {
        extractFunctions(file, kt?.declarations)
    }

    override val properties: MutableList<KibbleProperty> by lazy {
        extractProperties(file, kt?.declarations)
    }

    var initBlock: String? = null

    var constructor = Constructor(file)
        private set
    val secondaries: MutableList<SecondaryConstructor> = mutableListOf()

    internal constructor(file: KibbleFile, kt: KtClass) : this(file, kt.name ?: "") {
        this.kt = kt

        modality = Modal.apply(kt.modalityModifier())
        visibility = Visible.apply(kt.visibilityModifier())

        typeParameters = GenericCapable.extractFromTypeParameters(file, kt.typeParameters)

        kt.primaryConstructor?.let {
            constructor = Constructor(file, this, it)
        }
        kt.secondaryConstructors.forEach {
            secondaries += SecondaryConstructor(file, it)
        }

        superType = extractSuperType(file, kt.superTypeListEntries)
        superTypes = extractSuperTypes(file, kt.superTypeListEntries)
        superCallArgs = extractSuperCallArgs(kt.superTypeListEntries)
        annotations = extractAnnotations(file, kt.annotationEntries)
    }

    fun addSuperType(type: String) {
        superType = KibbleType.from(file, type)
    }
    /**
     * Adds a secondary constructor to this class
     *
     * @return the new constructor
     */
    fun addSecondaryConstructor(): SecondaryConstructor {
        return SecondaryConstructor(file).also {
            secondaries += it
        }
    }

    override fun addClass(name: String): KibbleClass {
        return KibbleClass(file, name).also {
            classes += it
        }
    }

    /**
     * Adds (or gets if it already exists) a companion object to this class
     *
     * @return the companion object
     */
    fun addCompanionObject(): KibbleObject {
        return companion() ?: KibbleObject(file, companion = true).also {
            objects.add(it)
        }
    }

    override fun addObject(name: String, isCompanion: Boolean): KibbleObject {
        return KibbleObject(file, name, isCompanion).also {
            objects += it
        }
    }

    override fun addFunction(name: String?, type: String, body: String): KibbleFunction {
        return KibbleFunction(file, name = name, proposed = type, body = body).also {
            functions += it
        }
    }

    override fun addProperty(name: String, type: String?, initializer: String?, modality: Modality, overriding: Boolean,
                             visibility: Visibility, mutability: Mutability, lateInit: Boolean, constructorParam: Boolean): KibbleProperty {
        return KibbleProperty(file, name, type?.let { KibbleType.from(file, type) }, initializer, modality, overriding, lateInit).also {
            it.visibility = visibility
            it.mutability = mutability
            it.constructorParam = constructorParam
            if (constructorParam) {
                constructor.parameters += it
            }
            properties += it
        }
    }

    /**
     * @return the string form of this class
     */
    override fun toString(): String {
        return "class $name"
    }

    override fun toSource(writer: SourceWriter, level: Int): SourceWriter {
        annotations.forEach { writer.writeln(it.toString(), level) }
        writer.write("$visibility${modality}class ", level)
        writer.write(name)
        if (!typeParameters.isEmpty()) {
            writer.write(typeParameters.joinToString(", ", prefix = "<", postfix = ">"))
        }

        constructor.toSource(writer, level)
        superType?.let {
            writer.write(" : $it")
            writer.write(superCallArgs.joinToString(prefix = "(", postfix = ")"))
        }
        if (!superTypes.isEmpty()) {
            writer.write(superTypes.joinToString(prefix = ", "))
        }
        val nonParamProps = properties.filter { !it.constructorParam }

        writer.writeln(" {")

        objects.filter { it.companion }
                .forEach { it.toSource(writer, level + 1) }

        secondaries.forEach { it.toSource(writer, level + 1) }
        initBlock?.let {
            writer.writeln("init {", level + 1)
            it.trimIndent().split("\n").forEach {
                writer.writeln(it, level + 2)
            }
            writer.writeln("}", level + 1)
            writer.writeln()
        }
        nonParamProps.forEach { it.toSource(writer, level + 1) }

        objects.filter { !it.companion }
                .forEach { it.toSource(writer, level + 1) }
        classes.forEach { it.toSource(writer, level + 1) }
        functions.forEach { it.toSource(writer, level + 1) }

        writer.write("}", level)

        writer.writeln()
        return writer
    }

    /**
     * Gets the companion object if it exists
     *
     * @return the companion object
     */
    fun companion(): KibbleObject? {
        return objects.firstOrNull { it.companion }
    }
}
