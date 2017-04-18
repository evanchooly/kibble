package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import com.antwerkz.kibble.model.Visibility.PUBLIC
import org.jetbrains.kotlin.psi.KtPrimaryConstructor

open class Constructor internal constructor(override val file: KibbleFile, val klass: KibbleClass) : Visible, ParameterHolder,
KibbleElement {
    internal constructor(klass: KibbleClass, kt: KtPrimaryConstructor) : this(klass.file, klass) {
        kt.valueParameters.forEach {
            if (it.hasValOrVar()) {
                val kibbleProperty = KibbleProperty(klass.file, klass, it)
                kibbleProperty.constructorParam = true
                this += kibbleProperty
                klass.properties += kibbleProperty
            } else {
                this += KibbleParameter(file, it)
            }
        }
        body = kt.bodyExpression?.text ?: ""
    }

    override var visibility: Visibility = PUBLIC
    override val parameters = mutableListOf<KibbleParameter>()
    var body: String = ""

    override fun toSource(writer: SourceWriter, level: Int): SourceWriter {
        if (parameters.size != 0) {
            writer.write("(")
            writer.write(parameters.joinToString(", "))
            writer.write(")")
        }

        return writer
    }
}