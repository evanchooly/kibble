package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
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
        Visible, Modal<KibbleClass>, AnnotationHolder, PropertyHolder, Extendable, ClassOrObjectHolder, Packaged {

    private var kt: KtClass? = null
    override var pkgName: String?
        get() = file.pkgName
        set(value) {
            file.pkgName = value
        }
    override var superTypes = listOf<KibbleType>()
    override var superType: KibbleType? = null
    override var superCallArgs = listOf<String>()
    override var typeParameters = listOf<TypeParameter>()

    override var annotations = mutableListOf<KibbleAnnotation>()
    override val classes: MutableList<KibbleClass> by lazy {
        KibbleExtractor.extractClasses(kt?.declarations, file)
    }

    override val objects: MutableList<KibbleObject> by lazy {
        KibbleExtractor.extractObjects(file, kt?.declarations)
    }

    override val functions: MutableList<KibbleFunction> by lazy {
        KibbleExtractor.extractFunctions(file, kt?.declarations)
    }

    override val properties: MutableList<KibbleProperty> by lazy {
        KibbleExtractor.extractProperties(file, kt?.declarations)
    }

    var initBlock: String? = null

    var constructor = Constructor(file)
        private set
    val secondaries: MutableList<SecondaryConstructor> = mutableListOf()

    internal constructor(file: KibbleFile, kt: KtClass) : this(file, kt.name ?: "") {
        this.kt = kt
        Extendable.extractSuperInformation(file, this, kt)

        modality = Modal.apply(kt.modalityModifier())
        visibility = Visible.apply(kt.visibilityModifier())

        typeParameters = GenericCapable.extractFromTypeParameters(file, kt.typeParameters)

        kt.primaryConstructor?.let {
            constructor = Constructor(file, this, it)
        }
        kt.secondaryConstructors.forEach {
            secondaries += SecondaryConstructor(file, it)
        }
        extractAnnotations(file, kt.annotationEntries)
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
        return KibbleFunction(file, name = name, type = type, body = body).also {
            functions += it
        }
    }

    override fun addProperty(name: String, type: String?, initializer: String?, modality: Modality, overriding: Boolean,
                             visibility: Visibility, mutability: Mutability, lateInit: Boolean, constructorParam: Boolean): KibbleProperty {
        return KibbleProperty(file, name, type?.let { KibbleType.from(type) }, initializer, modality, overriding, lateInit).also {
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
