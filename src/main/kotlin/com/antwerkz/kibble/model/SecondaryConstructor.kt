package com.antwerkz.kibble.model

import org.jetbrains.kotlin.psi.KtSecondaryConstructor

class SecondaryConstructor(): ParameterHolder {
    internal constructor(kt: KtSecondaryConstructor): this() {
            kt.valueParameters.forEach {
                this += Parameter(it)
            }
            body = kt.bodyExpression?.text  ?: ""
    }

    var body: String = ""
    override val parameters = mutableListOf<Parameter>()
}