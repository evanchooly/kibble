package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import org.jetbrains.kotlin.psi.KtSecondaryConstructor

class SecondaryConstructor internal constructor(klass: KibbleClass): Constructor(klass) {
    internal constructor(klass: KibbleClass, kt: KtSecondaryConstructor): this(klass) {
            kt.valueParameters.forEach {
                this += KibbleParameter(it)
            }
            body = kt.bodyExpression?.text ?: ""
    }

    override fun toSource(writer: SourceWriter, level: Int) = TODO("no source template yet")
}