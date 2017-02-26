package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import org.jetbrains.kotlin.psi.KtSecondaryConstructor

class SecondaryConstructor(): Constructor() {
    internal constructor(kt: KtSecondaryConstructor): this() {
            kt.valueParameters.forEach {
                this += Parameter(it)
            }
            body = kt.bodyExpression?.text ?: ""
    }

    override fun toSource(writer: SourceWriter, indentationLevel: Int) = TODO("no source template yet")
}