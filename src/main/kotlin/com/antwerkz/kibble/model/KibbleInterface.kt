package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import com.antwerkz.kibble.model.KibbleExtractor.extractAnnotations
import com.antwerkz.kibble.model.KibbleExtractor.extractClasses
import com.antwerkz.kibble.model.KibbleExtractor.extractFunctions
import com.antwerkz.kibble.model.KibbleExtractor.extractInterfaces
import com.antwerkz.kibble.model.KibbleExtractor.extractObjects
import com.antwerkz.kibble.model.KibbleExtractor.extractProperties
import com.antwerkz.kibble.model.KibbleExtractor.extractSuperTypes
import com.antwerkz.kibble.model.Visibility.PUBLIC
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.psiUtil.visibilityModifier

/**
 * Represents an annotation in kotlin source code.
 *
 * @property name the class name
 * @property initBlock any custom init block for this class
 * @property constructor the primary constructor for this class
 * @property secondaries the secondary constructors this class
 */
class KibbleInterface internal constructor(val file: KibbleFile, var name: String = "",
                                           override var visibility: Visibility = PUBLIC) : KibbleElement, FunctionHolder, GenericCapable,
        Visible, AnnotationHolder, PropertyHolder, ClassOrObjectHolder {

    override var typeParameters = mutableListOf<TypeParameter>()
    override val annotations: MutableList<KibbleAnnotation> = mutableListOf()
    override val classes: MutableList<KibbleClass> = mutableListOf()
    override val interfaces: MutableList<KibbleInterface> = mutableListOf()
    override val objects: MutableList<KibbleObject> = mutableListOf()
    override val functions: MutableList<KibbleFunction> = mutableListOf()
    override val properties: MutableList<KibbleProperty> = mutableListOf()
    val superTypes: MutableList<KibbleType> = mutableListOf()

    internal constructor(file: KibbleFile, kt: KtClass) : this(file, kt.name ?: "") {
        visibility = Visible.apply(kt.visibilityModifier())
        typeParameters = GenericCapable.extractFromTypeParameters(kt.typeParameters)

        superTypes += extractSuperTypes(kt.superTypeListEntries)
        annotations += extractAnnotations(kt.annotationEntries)

        interfaces += extractInterfaces(file, kt.declarations)
        classes += extractClasses(file, kt.declarations)
        objects += extractObjects(file, kt.declarations)
        functions += extractFunctions(kt.declarations)
        properties += extractProperties(kt.declarations)

    }

    fun addSuperType(type: String) {
        superTypes += KibbleType.from(type)
    }

    override fun addClass(name: String): KibbleClass {
        return KibbleClass(file, name).also {
            classes += it
        }
    }

    override fun addInterface(name: String): KibbleInterface {
        return KibbleInterface(file, name).also {
            interfaces += it
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
        return KibbleFunction(name = name, type = KibbleType.from(type), body = body).also {
            functions += it
        }
    }

    override fun addProperty(name: String, type: String?, initializer: String?, modality: Modality, overriding: Boolean,
                             visibility: Visibility, mutability: Mutability, lateInit: Boolean, constructorParam: Boolean): KibbleProperty {
        return KibbleProperty(name, type?.let { KibbleType.from(type) }, initializer, modality, overriding, lateInit).also {
            it.visibility = visibility
            it.mutability = mutability
            it.constructorParam = constructorParam
            properties += it
        }
    }

    /**
     * @return the string form of this interface
     */
    override fun toString(): String {
        return "interface $name"
    }

    override fun toSource(writer: SourceWriter, level: Int): SourceWriter {
        annotations.forEach { writer.writeln(it.toString(), level) }
        writer.write("${visibility}interface ", level)
        writer.write(name)
        if (!typeParameters.isEmpty()) {
            writer.write(typeParameters.joinToString(", ", prefix = "<", postfix = ">"))
        }

        if (!superTypes.isEmpty()) {
            writer.write(superTypes.joinToString(", ", prefix = ": "))
        }

        writer.writeln(" {")

        objects.filter { it.companion }
                .forEach { it.toSource(writer, level + 1) }

        properties.forEach { it.toSource(writer, level + 1) }

        objects.filter { !it.companion }
                .forEach { it.toSource(writer, level + 1) }
        interfaces.forEach { it.toSource(writer, level + 1) }
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

    override fun collectImports(file: KibbleFile) {
        properties.forEach { it.collectImports(file) }
        interfaces.forEach { it.collectImports(file) }
        classes.forEach { it.collectImports(file) }
        objects.forEach { it.collectImports(file) }
        functions.forEach { it.collectImports(file) }
        superTypes.forEach { file.resolve(it) }
    }
}
