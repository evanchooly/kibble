package com.antwerkz.kibble.model

import org.jetbrains.kotlin.psi.KtNullableType
import org.jetbrains.kotlin.psi.KtTypeReference
import org.jetbrains.kotlin.psi.KtUserType

data class KotlinType(val type: String, val parameters: List<String> = listOf<String>(), val nullable: Boolean) {
    companion object {
        fun from(typeReference: KtTypeReference?): KotlinType? {
            val typeElement = typeReference?.typeElement
            return if (typeElement != null) {
                when (typeElement) {
                    is KtUserType -> KotlinType(qualify(typeElement) ?: "", typeElement.typeArguments.map { it.text }, false)
                    is KtNullableType -> {
//                        typeElement.innerType
                        val type = typeElement.innerType as KtUserType
                        KotlinType(qualify(type) ?: "", type.typeArguments.map { it.text }, nullable = true)
                    }
                    else -> null
                }
            } else null
        }

        private fun qualify(type: KtUserType?): String? {
            return type?.referencedName?.let { name ->
                qualify(type.qualifier)?.let { "$it.$name" } ?: name
            }
        }
    }

    override fun toString(): String {
        return type +
                (if (nullable) "?" else "") +
                if (parameters.isNotEmpty()) parameters.joinToString(prefix = "<", postfix = ">") else ""
    }
}