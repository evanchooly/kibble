package com.antwerkz.kibble.model

import org.jetbrains.kotlin.psi.KtParameter
import org.jetbrains.kotlin.psi.psiUtil.modalityModifier

data class Parameter(val name: String,
                     val type: String,
                     val defaultValue: String? = null) : KotlinElement, Mutable {
    override var mutability: Mutability? = null

    companion object  {
        fun evaluate(kt: KtParameter): Parameter {
            val parameter = Parameter(kt.name!!, kt.typeReference?.text
                    ?: throw IllegalArgumentException("Unknown type: $kt"))
            if(kt.hasValOrVar()) {
                parameter.addModifier(kt.valOrVarKeyword?.text)
            }


            return parameter
        }

    }
    override fun toString(): String {
        return "${if (mutability != null) (mutability.toString() + " ") else "" }$name: $type"
    }
}