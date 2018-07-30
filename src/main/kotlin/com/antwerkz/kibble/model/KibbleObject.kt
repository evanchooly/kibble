package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import com.antwerkz.kibble.model.Visibility.PUBLIC

/**
 * Defines an object type
 *
 * @property name the object name
 * @property companion true if this object is a companion object
 * @property initBlock any custom init block for this class
 */
class KibbleObject internal constructor(val name: String? = null, val companion: Boolean = false)
    : KibbleElement, ClassOrObjectHolder, PropertyHolder, FunctionHolder, AnnotationHolder, Polymorphic, Visible {

    val superCallArgs = mutableListOf<KibbleArgument>()
    var extends: KibbleType? = null
    val implements: MutableList<KibbleType> = mutableListOf()

    override var visibility: Visibility = PUBLIC
    override val annotations = mutableListOf<KibbleAnnotation>()
    override val classes = mutableListOf<KibbleClass>()
    override val objects = mutableListOf<KibbleObject>()
    override val functions = mutableListOf<KibbleFunction>()
    override val properties = mutableListOf<KibbleProperty>()
    var initBlock: String? = null

    override fun extends(type: KibbleType, arguments: List<KibbleArgument>) {
        extends = type
        superCallArgs += arguments
    }

    override fun implements(type: KibbleType) {
        implements += type
    }

    override fun addClass(name: String): KibbleClass {
        return KibbleClass(name).also {
            classes += it
        }
    }

    override fun addObject(name: String, isCompanion: Boolean): KibbleObject {
        return KibbleObject(name = name, companion = isCompanion).also {
            objects += it
        }
    }

    override fun addFunction(name: String?, type: String, body: String): KibbleFunction {
        return KibbleFunction(name, type = KibbleType.from(type), body = body).also {
            functions += it
        }
    }

/*
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
*/

    /**
     * @return the string/source form of this type
     */
    override fun toString() = toSource().toString()

    override fun collectImports(file: KibbleFile) {
        objects.forEach { it.collectImports(file) }
        classes.forEach { it.collectImports(file) }
        functions.forEach { it.collectImports(file) }
        properties.forEach { it.collectImports(file) }
    }

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
        extends?.let {
            writer.write(": $it")
            writer.write(superCallArgs.joinToString(prefix = "(", postfix = ")"))
        }
        if (!implements.isEmpty()) {
            writer.write(implements.joinToString(prefix = ", "))
        }
        if (!properties.isEmpty() || !functions.isEmpty() || !classes.isEmpty()) {
            writer.writeln(" {")

            properties.forEach { it.toSource(writer, level + 1) }
            initBlock?.let {
                writer.writeln("init {", level + 1)
                it.trimIndent().split("\n").forEach {
                    writer.writeln(it, level + 2)
                }
                writer.writeln("}", level + 1)
                writer.writeln()
            }

            functions.forEach { it.toSource(writer, level + 1) }
            classes
                    .filter { it.isInterface }
                    .forEach { it.toSource(writer, level + 1) }
            classes
                    .filter { !it.isInterface }
                    .forEach { it.toSource(writer, level + 1) }

            writer.write("}", level)
        }
        writer.writeln()
        return writer
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as KibbleObject

        if (name != other.name) return false
        if (companion != other.companion) return false
        if (superCallArgs != other.superCallArgs) return false
        if (extends != other.extends) return false
        if (implements != other.implements) return false
        if (visibility != other.visibility) return false
        if (annotations != other.annotations) return false
        if (classes != other.classes) return false
        if (objects != other.objects) return false
        if (functions != other.functions) return false
        if (properties != other.properties) return false
        if (initBlock != other.initBlock) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name?.hashCode() ?: 0
        result = 31 * result + companion.hashCode()
        result = 31 * result + superCallArgs.hashCode()
        result = 31 * result + (extends?.hashCode() ?: 0)
        result = 31 * result + implements.hashCode()
        result = 31 * result + visibility.hashCode()
        result = 31 * result + annotations.hashCode()
        result = 31 * result + classes.hashCode()
        result = 31 * result + objects.hashCode()
        result = 31 * result + functions.hashCode()
        result = 31 * result + properties.hashCode()
        result = 31 * result + (initBlock?.hashCode() ?: 0)
        return result
    }

}