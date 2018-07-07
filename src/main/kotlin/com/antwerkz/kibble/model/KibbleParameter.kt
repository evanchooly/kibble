package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import com.antwerkz.kibble.model.Mutability.NEITHER
import com.antwerkz.kibble.model.Mutability.VAL
import com.antwerkz.kibble.model.Visibility.NONE

/**
 * Defines a function parameter
 *
 * @property name the parameter name
 * @property type the parameter type
 * @property initializer the parameter initializer
 */
open class KibbleParameter internal constructor(val name: String? = null, val type: KibbleType? = null,
                                                var initializer: String? = null,
                                                var vararg: Boolean = false)
    : KibbleElement, AnnotationHolder, GenericCapable, Mutable, Visible {

    override val annotations =  mutableListOf<KibbleAnnotation>()
    override var mutability: Mutability = NEITHER
        set(value) {
            field = if (value != NEITHER) value else VAL
        }
    override var visibility: Visibility = NONE
        set(value) {
            field = if (value != NEITHER) value else NONE
        }
    override var typeParameters = mutableListOf<TypeParameter>()

    /**
     * @return the string/source form of this type
     */
    override fun toString(): String {
        return toSource().toString()
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
        writer.write("$visibility$mutability")
        if(vararg) {
            writer.write("vararg ")
        }
        name?.let { writer.write(name) }
        type?.let {
            name?.let { writer.write(": ") }
            writer.write("$it")
        }
        initializer?.let {
            writer.write(" = $it")
        }

        return writer
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
        if (mutability != other.mutability) return false
        if (visibility != other.visibility) return false

        return true
    }

    /**
     * @return the hash code
     */
    override fun hashCode(): Int {
        var result = name?.hashCode() ?: 0
        result = 31 * result + (type?.hashCode() ?: 0)
        result = 31 * result + (initializer?.hashCode() ?: 0)
        result = 31 * result + mutability.hashCode()
        result = 31 * result + visibility.hashCode()
        return result
    }
}