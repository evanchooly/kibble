package com.antwerkz.kibble.model

interface GenericCapable {
    val parameters: MutableList<TypeParameter>
}

data class TypeParameter(val name: String, val modifier: ParameterModifier? = null) {
    override fun toString(): String {
        return (modifier?.let { it.name.toLowerCase() + " "} ?: "") + name
    }
}

enum class ParameterModifier {
    IN,
    OUT
}
