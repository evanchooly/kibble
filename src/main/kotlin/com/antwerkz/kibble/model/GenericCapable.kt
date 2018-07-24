package com.antwerkz.kibble.model

interface GenericCapable {
    val typeParameters: MutableList<TypeParameter>

    fun addTypeParameter(type: String, variance: TypeParameterVariance? = null, bounds: String? = null) {
        addTypeParameter(KibbleType.from(type), variance, bounds)
    }

    fun addTypeParameter(type: KibbleType, variance: TypeParameterVariance? = null, bounds: String? = null) {
        typeParameters += TypeParameter(type, variance, bounds?.let { KibbleType.from(it)})
    }
}

/**
 * Defines a type parameter for an element
 *
 * @property type the type name
 * @property variance in/out
 * @property bounds the type bounds of the parameter
 */
class TypeParameter internal constructor(val type: KibbleType?, val variance: TypeParameterVariance? = null,
                                         val bounds: KibbleType? = null) {

    override fun toString(): String {
        return ((variance?.let { "$it " } ?: "") + (type ?: "")).trim() + (bounds?.let { ": $it" } ?: "")
    }

    fun collectImports(file: KibbleFile) {
        type?.let { file.resolve(type) }
        bounds?.let { file.resolve(it) }
    }
}

enum class TypeParameterVariance(val label: String) {
    IN("in"),
    OUT("out"),
    STAR("*");

    override fun toString(): String {
        return label
    }
}