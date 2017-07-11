package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import com.antwerkz.kibble.model.Visibility.PUBLIC
import org.jetbrains.kotlin.psi.KtObjectDeclaration
import org.jetbrains.kotlin.psi.psiUtil.visibilityModifier

/**
 * Defines an object type
 *
 * @property name the object name
 * @property companion true if this object is a companion object
 */
class KibbleObject internal constructor(override val file: KibbleFile, val name: String? = null, val companion: Boolean = false)
    : Annotatable, ClassOrObjectHolder, Extendable, FunctionHolder, KibbleElement, PropertyHolder, Visible {

    override var superTypes = listOf<KibbleType>()
    override var superType: KibbleType? = null
    override var superCallArgs = listOf<String>()

    override var visibility: Visibility = PUBLIC
    override val functions = mutableListOf<KibbleFunction>()
    override val properties = mutableListOf<KibbleProperty>()
    override var annotations = mutableListOf<KibbleAnnotation>()
    override val classes = mutableListOf<KibbleClass>()
    override val objects = mutableListOf<KibbleObject>()

    internal constructor(file: KibbleFile, kt: KtObjectDeclaration): this(file, kt.name, kt.isCompanion()) {
        Extendable.extractSuperInformation(this, kt)
        visibility = Visible.apply(kt.visibilityModifier())

        extractAnnotations(kt.annotationEntries)
        kt.getBody()?.declarations?.let {
            extractClassesObjects(file, it)
            extractFunctions(it)
            extractProperties(file, it)
        }
    }

    override fun addClass(name: String): KibbleClass {
        return KibbleClass(file).also {
            classes += it
        }
    }
    override fun addObject(name: String, isCompanion: Boolean): KibbleObject {
        return KibbleObject(file, name = name, companion = isCompanion).also {
            objects += it
        }
    }

    override fun addFunction(name: String?, type: String, body: String): KibbleFunction {
        return KibbleFunction(name, type = type, body = body).also {
            functions += it
        }
    }

    override fun addProperty(name: String, type: String?, initializer: String?, modality: Modality, overriding: Boolean,
                             visibility: Visibility, mutability: Mutability, lateInit: Boolean, constructorParam: Boolean): KibbleProperty {
        if (constructorParam) {
            throw IllegalArgumentException("Object properties can not also be constructor parameters")
        }
        return KibbleProperty(name, type?.let { KibbleType.from(type) }, initializer, modality, overriding, lateInit).also {
            it.visibility = visibility
            properties += it
        }
    }

    /**
     * @return the string/source form of this type
     */
    override fun toString() = toSource().toString()

    /**
     * @return the string/source form of this type
     */
    override fun toSource(writer: SourceWriter, level: Int): SourceWriter {
        annotations.forEach { writer.writeln(it.toString(), level) }
        writer.write(visibility.toString(), level)
        if (companion) {
            writer.write("companion ")
        }
        writer.write("object")
        name?.let {
            writer.write(" $it")
        }
        superType?.let {
            writer.write(": $it")
            writer.write(superCallArgs.joinToString(prefix = "(", postfix = ")"))
        }
        if (!superTypes.isEmpty()) {
            writer.write(superTypes.joinToString(prefix = ", "))
        }
        if (!properties.isEmpty() || !functions.isEmpty() || !classes.isEmpty()) {
            writer.writeln(" {")

            properties.forEach { it.toSource(writer, level + 1) }
            functions.forEach { it.toSource(writer, level + 1) }
            classes.forEach { it.toSource(writer, level + 1) }

            writer.write("}", level)
        }
        writer.writeln()
        return writer
    }

    /**
     * @return true if `other` is equal to this
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as KibbleObject

        if (name != other.name) return false
        if (companion != other.companion) return false
        if (superTypes != other.superTypes) return false
        if (superType != other.superType) return false
        if (superCallArgs != other.superCallArgs) return false
        if (visibility != other.visibility) return false
        if (functions != other.functions) return false
        if (properties != other.properties) return false
        if (annotations != other.annotations) return false
        if (classes != other.classes) return false
        if (objects != other.objects) return false

        return true
    }

    /**
     * @return the hash code
     */
    override fun hashCode(): Int {
        var result = name?.hashCode() ?: 0
        result = 31 * result + companion.hashCode()
        result = 31 * result + superTypes.hashCode()
        result = 31 * result + (superType?.hashCode() ?: 0)
        result = 31 * result + superCallArgs.hashCode()
        result = 31 * result + visibility.hashCode()
        result = 31 * result + functions.hashCode()
        result = 31 * result + properties.hashCode()
        result = 31 * result + annotations.hashCode()
        result = 31 * result + classes.hashCode()
        result = 31 * result + objects.hashCode()
        return result
    }
}