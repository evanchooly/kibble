package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble
import org.jetbrains.kotlin.psi.KtFunctionType
import org.jetbrains.kotlin.psi.KtNullableType
import org.jetbrains.kotlin.psi.KtTypeReference
import org.jetbrains.kotlin.psi.KtUserType

/**
 * Specifies the type information of a property or parameter
 *
 * @property name the name of the type
 * @property typeParameters the parameterized types of this type
 * @property nullable does this type support null values?
 */
open class KibbleType internal constructor(val name: String,
                                           override var typeParameters: List<TypeParameter> = listOf<TypeParameter>(),
                                           val nullable: Boolean = false) : GenericCapable {
    companion object {
        /**
         * Creates a KibbleType from ths string
         *
         * @return the new KibbleType
         */
        fun from(type: String): KibbleType {
            return Kibble.parseSource("val temp: $type").properties[0].type!!
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
     * Gives the fully qualified name of this type complete with generic type parameters
     */
    val fullName: String
        get() = toString()
    /**
     * Gives only the class name portion of any FQCN
     */
    val className: String by lazy {
        name.dropWhile { !it.isUpperCase() }
    }
    /**
     * Gives the package component of any FQCN or null if it doesn't have a package specified
     */
    val pkgName: String? by lazy {
        val name = name.split(".")
                .dropLastWhile { it[0].isUpperCase() }
                .joinToString(".")
        if (name == "") null else name
    }

    /**
     * @return the string/source form of this type
     */
    override fun toString(): String {
        return name + (if (typeParameters.isNotEmpty()) typeParameters.joinToString(prefix = "<", postfix = ">") else "") +
                if (nullable) "?" else ""
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
        result = 31 * result + typeParameters.hashCode()
        result = 31 * result + nullable.hashCode()
        return result
    }

}