package com.antwerkz.kibble.model

import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement
import org.jetbrains.kotlin.psi.KtTypeParameter
import org.jetbrains.kotlin.psi.KtTypeProjection
import org.jetbrains.kotlin.psi.psiUtil.allChildren

interface GenericCapable {
    companion object {
        internal fun extractFromTypeParameters(file: KibbleFile, parameters: MutableList<KtTypeParameter>): MutableList<TypeParameter> {
            return parameters.map {
                val modifier: ParameterModifier? = it.modifierList
                        ?.allChildren
                        ?.filterIsInstance<LeafPsiElement>()
                        ?.map { ParameterModifier.valueOf(it.text.toUpperCase()) }
                        ?.firstOrNull()

                TypeParameter(file.normalize(KibbleType.from(it.name!!)), modifier, it.extendsBound?.text)
            }
                    .toMutableList()
        }

        internal fun extractFromTypeProjections(file: KibbleFile, parameters: List<KtTypeProjection>): List<TypeParameter> {
            val map: List<TypeParameter> = parameters.map {
                TypeParameter(file.normalize(KibbleType.from(it.text)))
            }
            return map
        }

    }

    val typeParameters: List<TypeParameter>
}

/**
 * Defines a type parameter for an element
 *
 * @property type the type name
 * @property modifier in/out
 * @property bounds the type bounds of the parameter
 */
data class TypeParameter(val type: KibbleType, val modifier: ParameterModifier? = null, val bounds: String? = null) {
    override fun toString(): String {
        return (modifier?.let { "$it " } ?: "") + type + (bounds?.let { ": $it"} ?: "")
    }
}

enum class ParameterModifier {
    IN,
    OUT;

    override fun toString(): String {
        return name.toLowerCase()
    }
}