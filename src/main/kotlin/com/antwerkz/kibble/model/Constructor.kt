package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import com.antwerkz.kibble.model.Visibility.PUBLIC
import org.jetbrains.kotlin.psi.KtPrimaryConstructor

open class Constructor internal constructor(val klass: KibbleClass) : Visible, ParameterHolder, KibbleElement {
    internal constructor(klass: KibbleClass, kt: KtPrimaryConstructor) : this(klass) {
        kt.valueParameters.forEach {
            val parameter = KibbleParameter(it)
            this += parameter
        }
        body = kt.bodyExpression?.text ?: ""
    }

    override var visibility: Visibility = PUBLIC
    override val parameters = mutableListOf<KibbleParameter>()
    var body: String = ""

    override fun toSource(writer: SourceWriter, level: Int) {
        if (parameters.size != 0) {
            writer.write("(")
            writer.write(parameters.joinToString(", "))
            writer.write(")")
        }
    }
}