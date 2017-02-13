package com.antwerkz.kibble.model

import org.jetbrains.kotlin.psi.KtParameter

data class Parameter(val name: String, val type: KotlinType, val defaultValue: String? = null) : KotlinElement, Mutable {

    internal constructor(kt: KtParameter) : this(kt.name!!, KotlinType.from(kt.typeReference)
            ?: throw IllegalArgumentException("Unknown type: $kt")) {
        if (kt.hasValOrVar()) {
            addModifier(kt.valOrVarKeyword?.text)
        }
    }

    override var mutability: Mutability? = null

    override fun toString(): String {
        return "${if (mutability != null) "$mutability" else ""}$name: $type"
    }
}