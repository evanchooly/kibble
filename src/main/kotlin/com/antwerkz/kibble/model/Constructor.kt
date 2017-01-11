package com.antwerkz.kibble.model

import com.antwerkz.kibble.model.Visibility.PUBLIC
import org.jetbrains.kotlin.psi.KtPrimaryConstructor

class Constructor() : Visible, ParameterHolder {
    internal constructor(kt: KtPrimaryConstructor): this() {
        kt.valueParameters.forEach {
            this += Parameter(it)
        }
        body = kt.bodyExpression?.text ?: ""
    }

    override var visibility: Visibility = PUBLIC
    override val parameters = mutableListOf<Parameter>()
    var body: String = ""
}