package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble
import org.jetbrains.kotlin.psi.KtFunctionType
import org.jetbrains.kotlin.psi.KtNullableType
import org.jetbrains.kotlin.psi.KtTypeReference
import org.jetbrains.kotlin.psi.KtUserType

/**
 * Specifies the type information of a property or parameter
 *
 * @param value the type
 * @property typeParameters the type parameters of this type
 * @property nullable does this type support null values?
 */
open class KibbleType internal constructor(value: String, override val typeParameters: List<TypeParameter> = listOf<TypeParameter>(),
                                           val nullable: Boolean = false) : GenericCapable {

    /**
     * Gives the fully qualified name of this type complete with generic type parameters
     */
    val name: String

    /**
     * Gives only the class name portion of any FQCN
     */
    val className: String

    /**
     * Gives the package component of any FQCN or null if it doesn't have a package specified
     */
    val pkgName: String?

    /**
     * Gives the FQCN (sans type parameters) or the class name if it doesn't have a package specified
     */
    val fqcn: String

    init {
        val raw = value.substringBefore("<")
        val name = raw.split(".")
                .dropLastWhile { it != "" && it[0].isUpperCase() }
                .joinToString(".")
        pkgName = if (name == "") null else name

        className = raw.split(".")
                .dropWhile { it != "" && !it[0].isUpperCase() }
                .joinToString(".")

        this.name = raw + (if (typeParameters.isNotEmpty()) typeParameters.joinToString(prefix = "<", postfix = ">") else "") +
                if (nullable) "?" else ""

        fqcn = pkgName?.let { "$pkgName.$className"} ?: className
    }

    companion object {
        /**
         * Creates a KibbleType from ths string
         *
         * @return the new KibbleType
         */
        fun from(type: String): KibbleType {
            return Kibble.parseSource("val temp: $type").properties[0].type!!
        }

        fun resolve(type: KibbleType, pkgName: String?): KibbleType {
            return KibbleType(pkgName?.let { "$pkgName.${type.className}" } ?: type.className,
                    type.typeParameters, type.nullable)
        }

        internal fun from(kt: KtTypeReference?): KibbleType? {
            return kt?.typeElement.let {
                when (it) {
                    is KtUserType -> extractType(it)
                    is KtNullableType -> extractType(it.innerType as KtUserType, true)
                    is KtFunctionType -> KibbleFunctionType(it)
                    else -> it?.let { throw IllegalArgumentException("unknown type $it") }
                }
            }
        }

        internal fun extractType(typeElement: KtUserType, nullable: Boolean = false): KibbleType {
            val name = (typeElement.qualifier?.text?.let { "$it." } ?: "") +
                    (typeElement.referencedName ?: "")
            val parameters = GenericCapable.extractFromTypeProjections(typeElement.typeArguments)
            return KibbleType(name, parameters, nullable)
        }
    }

    /**
     * @return the string/source form of this type
     */
    override fun toString(): String {
        return name
    }

    /**
     * @return true if `other` is equal to this
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as KibbleType

        if (name != other.name) return false
        if (typeParameters != other.typeParameters) return false
        if (nullable != other.nullable) return false

        return true
    }

    /**
     * @return the hashcode for this type
     */
    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + nullable.hashCode()
        return result
    }

}