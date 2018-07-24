package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble

/**
 * Specifies the type information of a property or parameter
 *
 * @property className the class name of this type
 * @property pkgName the package name of this type
 * @property typeParameters the type parameters of this type
 * @property nullable does this type support null values?
 */
open class KibbleType internal constructor(pkgName: String? = null, val className: String,
                                           override val typeParameters: MutableList<TypeParameter> = mutableListOf(),
                                           val nullable: Boolean = false) : GenericCapable, Comparable<KibbleType> {

    internal constructor(type: KibbleType, nullable: Boolean = false) : this(type.pkgName, type.className, type.typeParameters, nullable)

    companion object {
        fun from(type: Class<Any>) = KibbleType(pkgName = type.`package`.name, className = type.simpleName)

        fun from(type: String): KibbleType {
            return if (!type.contains("*") && (type.contains(".") || type.contains("<"))) {
                Kibble.parseSource("val temp: $type").properties[0].type!!
            } else {
                KibbleType(className = type)
            }
        }
    }

    var pkgName: String? = pkgName
        get() = if(field != "") field else null

    /**
     * Gives the fully qualified class name for this type
     */
    fun fqcn() = (pkgName?.let { "$pkgName." } ?: "") + className

    internal var resolved = fqcn()

    /**
     * @return the string/source form of this type
     */
    override fun toString(): String {
        val list = mutableListOf(resolved)
        var base = list.joinToString(".") +
                (if (typeParameters.isNotEmpty()) typeParameters.joinToString(prefix = "<", postfix = ">") else "")
        if (nullable) base += "?"
        return base
    }

    override fun compareTo(other: KibbleType): Int {
        return fqcn().compareTo(other.fqcn())
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as KibbleType

        if (pkgName != other.pkgName) return false
        if (className != other.className) return false
        if (typeParameters != other.typeParameters) return false
        if (nullable != other.nullable) return false

        return true
    }

    override fun hashCode(): Int {
        var result = pkgName?.hashCode() ?: 0
        result = 31 * result + className.hashCode()
        result = 31 * result + typeParameters.hashCode()
        result = 31 * result + nullable.hashCode()
        return result
    }
}