package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble
import org.jetbrains.kotlin.psi.KtNullableType
import org.jetbrains.kotlin.psi.KtTypeReference
import org.jetbrains.kotlin.psi.KtUserType
import java.io.File

open class KotlinType(val name: String, var qualifiedName: String = name, val parameters: List<KotlinType> = listOf<KotlinType>(),
                      val nullable: Boolean = false) {
    companion object {
        fun  from(type: String): KotlinType {
            val temp = File.createTempFile("kibble-temp", ".kt")
            try {
                temp.deleteOnExit()
                temp.writeText("val temp: $type\n")
                return Kibble.parse(temp).properties[0].type
            } finally {
                temp.delete()
            }
        }

        fun from(typeReference: KtTypeReference?): KotlinType {
            val typeElement = typeReference?.typeElement
            return when (typeElement) {
                is KtUserType -> {
                    KotlinType(typeElement.referencedName ?: "", qualify(typeElement) ?: "",
                            typeElement.typeArguments.map { from(it.typeReference) })
                }
                is KtNullableType -> {
                    val type = typeElement.innerType as KtUserType
                    KotlinType(type.referencedName ?: "", qualify(type) ?: "",
                            type.typeArguments.map { from(it.typeReference) }, nullable = true)
                }
                else -> throw IllegalArgumentException("unknown type $typeElement")
            }
        }

        internal fun qualify(type: KtUserType?): String? {
            return type?.let {
                val qualified = type.referencedName
                type.qualifier?.let { qualify(type.qualifier).let { "$it.$qualified" } } ?: qualified
            }
        }
    }

    val fullName: String
        get() = toString()

    init {
        if (qualifiedName == "") {
            qualifiedName = name
        }
    }

    override fun toString(): String {
        return qualifiedName +
                (if (parameters.isNotEmpty()) parameters.joinToString(prefix = "<", postfix = ">") else "") +
                if (nullable) "?" else ""
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as KotlinType

        if (name != other.name) return false
        if (qualifiedName != name && other.qualifiedName != other.name) {
            if (qualifiedName != other.qualifiedName) return false
        }
        if (parameters != other.parameters) return false
        if (nullable != other.nullable) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + qualifiedName.hashCode()
        result = 31 * result + parameters.hashCode()
        result = 31 * result + nullable.hashCode()
        return result
    }
}