package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import com.antwerkz.kibble.model.Visibility.PUBLIC
import org.jetbrains.kotlin.psi.KtPrimaryConstructor

class Constructor() : Visible, ParameterHolder, KotlinElement {
    internal constructor(kt: KtPrimaryConstructor) : this() {
        kt.valueParameters.forEach {
            val parameter = Parameter(it)
            this += parameter
        }
        body = kt.bodyExpression?.text ?: ""
    }

    override var visibility: Visibility = PUBLIC
    override val parameters = mutableListOf<Parameter>()
    var body: String = ""

    override fun toSource(writer: SourceWriter, indentationLevel: Int) {
        writer.write("(")
        writer.write(parameters.joinToString(", "))
        writer.write(")")
    }
}