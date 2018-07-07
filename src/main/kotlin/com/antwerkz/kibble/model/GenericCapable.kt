package com.antwerkz.kibble.model

interface GenericCapable {
    val typeParameters: MutableList<TypeParameter>

    fun addTypeParameter(type: String, kind: TypeParameterKind? = null, bounds: String? = null) {
        addTypeParameter(KibbleType.from(type), kind, bounds)
    }

    fun addTypeParameter(type: KibbleType, kind: TypeParameterKind? = null, bounds: String? = null) {
        typeParameters += TypeParameter(type, kind, bounds?.let { KibbleType.from(it)})
    }
}

/**
 * Defines a type parameter for an element
 *
 * @property type the type name
 * @property kind in/out
 * @property bounds the type bounds of the parameter
 */
class TypeParameter internal constructor(val type: KibbleType, val kind: TypeParameterKind? = null,
                                         val bounds: KibbleType? = null) {

    override fun toString(): String {
        return (kind?.let { "$it " } ?: "") + type + (bounds?.let { ": $it" } ?: "")
    }

    fun collectImports(file: KibbleFile) {
        file.resolve(type)
        bounds?.let { file.resolve(it) }
    }
}

enum class TypeParameterKind {
    IN,
    OUT,
    STAR;

    override fun toString(): String {
        return name.toLowerCase()
    }
}