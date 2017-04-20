package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble
import org.jetbrains.kotlin.psi.KtNullableType
import org.jetbrains.kotlin.psi.KtTypeReference
import org.jetbrains.kotlin.psi.KtUserType

open class KibbleType internal constructor(val file: KibbleFile, val name: String, val parameters: List<KibbleType> = listOf<KibbleType>(),
                                           val nullable: Boolean = false) {
    companion object {
        fun from(type: String): KibbleType {
             return Kibble.parseSource("val temp: $type").properties[0].type!!
        }

        internal fun from(file: KibbleFile, typeReference: KtTypeReference?): KibbleType? {
            val typeElement = typeReference?.typeElement
            return typeElement?.let {
                when (typeElement) {
                    is KtUserType -> {
                        extractType(file, typeElement)
                    }
                    is KtNullableType -> {
                        extractType(file, typeElement.innerType as KtUserType, true)
                    }
                    else -> throw IllegalArgumentException("unknown type $typeElement")
                }
            }
        }

        private fun extractType(file: KibbleFile, typeElement: KtUserType, nullable: Boolean = false): KibbleType {
            val name = (typeElement.qualifier?.text?.let { "$it." } ?: "") +
                    (typeElement.referencedName ?: "")
            val parameters = typeElement.typeArguments.map { from(file, it.typeReference)!! }

            return KibbleType(file, name, parameters, nullable)
        }
    }

    val fullName: String
        get() = toString()

    override fun toString(): String {
        return name + (if (parameters.isNotEmpty()) parameters.joinToString(prefix = "<", postfix = ">") else "") +
                if (nullable) "?" else ""
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as KibbleType

        if (name != other.name) return false
        if (parameters != other.parameters) return false
        if (nullable != other.nullable) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + parameters.hashCode()
        result = 31 * result + nullable.hashCode()
        return result
    }

}