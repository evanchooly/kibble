package com.antwerkz.kibble.model

import com.antwerkz.kibble.model.Visibility.PUBLIC
import org.jetbrains.kotlin.psi.KtPrimaryConstructor

class Constructor : Visible, ParameterHolder {
    companion object {
        fun evaluate(kt: KtPrimaryConstructor?): Constructor? {
            if (kt == null) {
                return null
            }
            val ctor = Constructor()
            kt.valueParameters.forEach {
                ctor += Parameter.evaluate(it)
            }
            ctor.body = kt.bodyExpression?.text  ?: ""
            return ctor
        }
    }

    override var visibility: Visibility = PUBLIC
    override val parameters = mutableListOf<Parameter>()
    var body: String = ""
}