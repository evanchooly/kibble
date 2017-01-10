package com.antwerkz.kibble.model

import org.jetbrains.kotlin.psi.KtSecondaryConstructor

class SecondaryConstructor: ParameterHolder {
    companion object {
        fun evaluate(kt: KtSecondaryConstructor): SecondaryConstructor {
            val ctor = SecondaryConstructor()
            kt.valueParameters.forEach {
                ctor += Parameter.evaluate(it)
            }
            ctor.body = kt.bodyExpression?.text  ?: ""
            return ctor
        }
    }

    var body: String = ""
    override val parameters = mutableListOf<Parameter>()
}