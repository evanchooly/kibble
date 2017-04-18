package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble
import org.jetbrains.kotlin.psi.KtNullableType
import org.jetbrains.kotlin.psi.KtTypeReference
import org.jetbrains.kotlin.psi.KtUserType

open class KibbleType internal constructor(val packageName: String? = null, val name: String,
                                           val parameters: List<KibbleType> = listOf<KibbleType>(),
                                           val nullable: Boolean = false) {
    companion object {
        fun from(file: KibbleFile, type: String): KibbleType {
            val temp = KibbleFile("temp")
            temp.imports += file.imports
            return Kibble.parseSource("""
${temp.toSource()}
val temp: $type
""").properties[0].type
        }

        fun from(file: KibbleFile, typeReference: KtTypeReference?): KibbleType {
            val typeElement = typeReference?.typeElement
            return when (typeElement) {
                is KtUserType -> {
                    val name = typeElement.referencedName ?: ""
                    val packageName = qualify(typeElement.qualifier)

                    var type: KibbleType? = null
                    if (packageName == null) {
                        type = file.getImport(name)?.type
                    }

                    type ?: KibbleType(packageName, name,
                                typeElement.typeArguments.map { from(file, it.typeReference) })

                }
                is KtNullableType -> {
                    val userType = typeElement.innerType as KtUserType
                    val name = userType.referencedName ?: ""
                    val packageName = qualify(userType.qualifier)

                    var type: KibbleType? = null
                    if (packageName == null) {
                        type = file.getImport(name)?.type
                    }

                    type ?: KibbleType(packageName, name,
                            userType.typeArguments.map { from(file, it.typeReference) })
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

    override fun toString(): String {
        return (packageName?.let { it + "." } ?: "") + name +
                (if (parameters.isNotEmpty()) parameters.joinToString(prefix = "<", postfix = ">") else "") +
                if (nullable) "?" else ""
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as KibbleType

        if (name != other.name) return false
        if (packageName != other.packageName) return false
        if (parameters != other.parameters) return false
        if (nullable != other.nullable) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (packageName?.hashCode() ?: 0)
        result = 31 * result + parameters.hashCode()
        result = 31 * result + nullable.hashCode()
        return result
    }

}