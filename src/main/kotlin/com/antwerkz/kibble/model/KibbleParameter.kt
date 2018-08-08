package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter

/**
 * Defines a function parameter
 *
 * @property name the parameter name
 * @property type the parameter type
 * @property initializer the parameter initializer
 */
class KibbleParameter internal constructor(val name: String? = null, val type: KibbleType? = null,
                                           var initializer: String? = null, var vararg: Boolean = false)
    : KibbleElement, AnnotationHolder, GenericCapable {

    override var annotations = listOf<KibbleAnnotation>()
        private set
    override var typeParameters = listOf<TypeParameter>()
        private set

    override fun addAnnotation(annotation: KibbleAnnotation) {
        annotations += annotation
    }

    override fun collectImports(file: KibbleFile) {
        type?.let { file.resolve(it) }
        annotations.forEach { it.collectImports(file) }
        typeParameters.forEach { it.collectImports(file) }
    }

    /**
     * @return the string/source form of this type
     */
    override fun toSource(writer: SourceWriter, level: Int): SourceWriter {
        writer {
            if (vararg) {
                write("vararg ")
            }
            name?.let { write(name) }
            writeType(type, name != null)
            writeInitializer(initializer)
        }
        return writer
    }

    override fun addTypeParameter(type: TypeParameter) {
        typeParameters += type
    }

    /**
     * @return true if `other` is equal to this
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as KibbleParameter

        if (name != other.name) return false
        if (type != other.type) return false
        if (initializer != other.initializer) return false

        return true
    }

    /**
     * @return the hash code
     */
    override fun hashCode(): Int {
        var result = name?.hashCode() ?: 0
        result = 31 * result + (type?.hashCode() ?: 0)
        result = 31 * result + (initializer?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "KibbleParameter(name=$name, type=$type, initializer=$initializer, vararg=$vararg, annotations=$annotations, typeParameters=$typeParameters)"
    }

}