package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import com.antwerkz.kibble.model.Mutability.NEITHER
import com.antwerkz.kibble.model.Mutability.VAL
import com.antwerkz.kibble.model.Visibility.NONE
import org.jetbrains.kotlin.psi.KtParameter

/**
 * Defines a function parameter
 *
 * @property name the parameter name
 * @property type the parameter type
 * @property initializer the parameter initializer
 */
open class KibbleParameter internal constructor(val name: String, val type: KibbleType?,
                                                var initializer: String? = null, var varargs: Boolean = false)
    : KibbleElement, GenericCapable, Mutable, Visible {

    @Suppress("LeakingThis")
    internal constructor(kt: KtParameter) : this(kt.name!!, KibbleType.from(kt.typeReference)) {
        mutability = Mutable.apply(kt.valOrVarKeyword)
        typeParameters.addAll(GenericCapable.extractFromTypeParameters(kt.typeParameters))
        varargs = kt.isVarArg
    }

    override var mutability: Mutability = NEITHER
    override var visibility: Visibility = NONE
        set(value) {
            field = value
            if (mutability != NEITHER) {
                mutability = VAL
            }
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
    }

    /**
     * @return the string/source form of this type
     */
    override fun toSource(writer: SourceWriter, level: Int): SourceWriter {
        writer.write("$visibility$mutability")
        if(varargs) {
            writer.write("vararg ")
        }
        name.let { writer.write(name) }
        type?.let {
            writer.write(": $it")
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
        var result = name.hashCode()
        result = 31 * result + (type?.hashCode() ?: 0)
        result = 31 * result + (initializer?.hashCode() ?: 0)
        result = 31 * result + mutability.hashCode()
        result = 31 * result + visibility.hashCode()
        return result
    }
}