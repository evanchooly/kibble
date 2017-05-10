package com.antwerkz.kibble.model

import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement
import org.jetbrains.kotlin.psi.KtTypeParameter
import org.jetbrains.kotlin.psi.KtTypeProjection
import org.jetbrains.kotlin.psi.psiUtil.allChildren

interface GenericCapable {
    companion object {
        internal fun extractFromTypeParameters(parameters: MutableList<KtTypeParameter>): MutableList<TypeParameter> {
            return parameters.map {
                val modifier: ParameterModifier? = it.modifierList
                        ?.allChildren
                        ?.filterIsInstance<LeafPsiElement>()
                        ?.map { ParameterModifier.valueOf(it.text.toUpperCase()) }
                        ?.firstOrNull()
                TypeParameter(it.name!!, modifier, it.extendsBound?.text)
            }
                    .toMutableList()
        }

        internal fun extractFromTypeProjections(parameters: List<KtTypeProjection>): List<TypeParameter> {
            val map: List<TypeParameter> = parameters.map {
                TypeParameter(it.typeReference?.text ?: "")
            }
            return map
        }

    }
    var typeParameters: List<TypeParameter>
}

/**
 * Defines a type parameter for an element
 *
 * @property name the type name
 * @property modifier in/out
 * @property bounds the type bounds of the parameter
 */
data class TypeParameter(val name: String, val modifier: ParameterModifier? = null, val bounds: String? = null) {
    override fun toString(): String {
        return (modifier?.let { it.name.toLowerCase() + " " } ?: "") + name + (bounds?.let { ": $it"} ?: "")
    }
}

enum class ParameterModifier {
    IN,
    OUT
}