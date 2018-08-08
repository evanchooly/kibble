package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter

interface GenericCapable {
    val typeParameters: List<TypeParameter>

    fun addTypeParameter(type: String, variance: TypeParameterVariance? = null, bounds: String? = null) {
        addTypeParameter(KibbleType.from(type), variance, bounds)
    }

    fun addTypeParameter(type: KibbleType, variance: TypeParameterVariance? = null, bounds: String? = null) {
        addTypeParameter(TypeParameter(type, variance, bounds?.let { KibbleType.from(it)}))
    }

    fun addTypeParameters(types: List<TypeParameter>) {
        types.forEach { addTypeParameter(it) }
    }

    fun addTypeParameter(type: TypeParameter)
}

/**
 * Defines a type parameter for an element
 *
 * @property type the type name
 * @property variance in/out
 * @property bounds the type bounds of the parameter
 */
data class TypeParameter internal constructor(val type: KibbleType?, val variance: TypeParameterVariance? = null,
                                         val bounds: KibbleType? = null): KibbleElement {

    override fun toSource(writer: SourceWriter, level: Int): SourceWriter {
        return writer {
            variance?.let { write("$it") }
            type?.let {
                variance?.let { write(" ") }
                it.toSource(this, level)
            }
            writeType(bounds)
        }
    }

    fun externalize(): String {
        val writer = SourceWriter()
        return writer {
            variance?.let { write("$it") }
            type?.let {
                variance?.let { write(" ") }
                write(it.externalize())
            }
            writeType(bounds)
        }.toString()
    }

    override fun collectImports(file: KibbleFile) {
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