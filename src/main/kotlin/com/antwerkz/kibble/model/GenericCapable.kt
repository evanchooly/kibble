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

                TypeParameter(file, KibbleType.from(file, it.name!!), modifier, it.extendsBound?.text)
            }
                    .toMutableList()
        }

        internal fun extractFromTypeProjections(file: KibbleFile, parameters: List<KtTypeProjection>): List<TypeParameter> {
            return parameters.map {
                TypeParameter(file, KibbleType.from(file, it.text))
            }
        }

    }

    val file: KibbleFile

    val typeParameters: MutableList<TypeParameter>

    fun addTypeParameter(type: String, modifier: ParameterModifier? = null, bounds: String? = null) {
        addTypeParameter(KibbleType.from(file, type), modifier, bounds)
    }

    fun addTypeParameter(type: KibbleType, modifier: ParameterModifier? = null, bounds: String? = null) {
        typeParameters += TypeParameter(file, type, modifier, bounds)
    }
}

/**
 * Defines a type parameter for an element
 *
 * @property type the type name
 * @property modifier in/out
 * @property bounds the type bounds of the parameter
 */
class TypeParameter internal constructor(file: KibbleFile, proposed: KibbleType, val modifier: ParameterModifier? = null,
                                         val bounds: String? = null) {
    val type: KibbleType by lazy { file.normalize(proposed)}

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