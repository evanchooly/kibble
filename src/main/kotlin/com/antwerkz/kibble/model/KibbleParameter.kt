package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import com.antwerkz.kibble.model.Mutability.NEITHER
import com.antwerkz.kibble.model.Mutability.VAL
import com.antwerkz.kibble.model.Visibility.NONE
import org.jetbrains.kotlin.psi.KtParameter

open class KibbleParameter internal constructor(val name: String, val type: KibbleType?, var initializer: String? = null) : KibbleElement,
        Mutable, Visible {

    internal constructor(file: KibbleFile, kt: KtParameter) : this(kt.name!!, KibbleType.from(file, kt.typeReference)) {
        mutability = Mutable.apply(kt.valOrVarKeyword)
    }

    override var mutability: Mutability = NEITHER
    override var visibility: Visibility = NONE
        set(value) {
            field = value
            if (mutability != NEITHER) {
                mutability = VAL
            }
        }

    override fun toString(): String {
        return toSource().toString()
    }

    override fun toSource(writer: SourceWriter, level: Int): SourceWriter {
        writer.write("$visibility${mutability}$name: $type", level)

        return writer
    }

    fun isParameterized(): Boolean = type?.parameters?.isEmpty()?.not() ?: false

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

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (type?.hashCode() ?: 0)
        result = 31 * result + (initializer?.hashCode() ?: 0)
        result = 31 * result + mutability.hashCode()
        result = 31 * result + visibility.hashCode()
        return result
    }
}