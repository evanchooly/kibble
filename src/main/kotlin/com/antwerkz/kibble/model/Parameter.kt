package com.antwerkz.kibble.model

import org.jetbrains.kotlin.psi.KtParameter

data class Parameter(val name: String, val type: String, val defaultValue: String? = null) : KotlinElement, Mutable {

    internal constructor(kt: KtParameter) : this(kt.name!!, kt.typeReference?.text
            ?: throw IllegalArgumentException("Unknown type: $kt")) {
        if (kt.hasValOrVar()) {
            addModifier(kt.valOrVarKeyword?.text)
        }
    }

    override var mutability: Mutability? = null

    override fun toString(): String {
        return "${if (mutability != null) (mutability.toString() + " ") else ""}$name: $type"
    }
}