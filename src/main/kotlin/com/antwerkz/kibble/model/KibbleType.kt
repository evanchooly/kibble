package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble
import org.jetbrains.kotlin.psi.KtNullableType
import org.jetbrains.kotlin.psi.KtTypeReference
import org.jetbrains.kotlin.psi.KtUserType

/**
 * Specifies the type information of a property or parameter
 *
 * @property name the name of the type
 * @property parameters the parameterized types of this type
 * @property nullable does this type support null values?
 */
open class KibbleType internal constructor(val name: String, val parameters: List<KibbleType> = listOf<KibbleType>(),
                                           val nullable: Boolean = false)/*: GenericCapable*/ {
    companion object {
        /**
         * Creates a KibbleType from ths string
         *
         * @return the new KibbleType
         */
        fun from(type: String): KibbleType {
            return Kibble.parseSource("val temp: $type").properties[0].type!!
        }

        internal fun from(file: KibbleFile, kt: KtTypeReference?): KibbleType? {
            return kt?.typeElement?.let {
                when (it) {
                    is KtUserType -> extractType(file, it)
                    is KtNullableType -> extractType(file, it.innerType as KtUserType, true)
                    else -> throw IllegalArgumentException("unknown type $it")
                }
            }
        }

        private fun extractType(file: KibbleFile, typeElement: KtUserType, nullable: Boolean = false): KibbleType {
            val name = (typeElement.qualifier?.text?.let { "$it." } ?: "") +
                    (typeElement.referencedName ?: "")
            val parameters = typeElement.typeArguments.map { from(file, it.typeReference)!! }

            return KibbleType(name, parameters, nullable)
        }
    }

    /**
     * Gives the fully qualified name of this type complete with generic type parameters
     */
    val fullName: String
        get() = toString()

    /**
     * @return the string/source form of this type
     */
    override fun toString(): String {
        return name + (if (parameters.isNotEmpty()) parameters.joinToString(prefix = "<", postfix = ">") else "") +
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
        if (parameters != other.parameters) return false
        if (nullable != other.nullable) return false

        return true
    }

    /**
     * @return the hashcode for this type
     */
    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + parameters.hashCode()
        result = 31 * result + nullable.hashCode()
        return result
    }

}

