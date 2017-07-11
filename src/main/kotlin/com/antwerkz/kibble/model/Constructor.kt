package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import com.antwerkz.kibble.model.Visibility.PUBLIC
import org.jetbrains.kotlin.psi.KtPrimaryConstructor

/**
 * Defines a constructor for a Class
 *
 * @property visibility the constructor visibility
 * @property parameters the constructor parameters
 * @property body the constructor body
 */
open class Constructor internal constructor() : Visible, ParameterHolder, KibbleElement {
    internal constructor(klass: KibbleClass, kt: KtPrimaryConstructor) : this() {
        kt.valueParameters.forEach {
            val kibbleProperty = KibbleProperty(it)
            if (it.hasValOrVar()) {
                kibbleProperty.constructorParam = true
                klass.properties += kibbleProperty
            }
            parameters += kibbleProperty
        }
        body = kt.bodyExpression?.text ?: ""
    }

    override var visibility: Visibility = PUBLIC
    override val parameters = mutableListOf<KibbleParameter>()
    var body: String? = null

    override fun toSource(writer: SourceWriter, level: Int): SourceWriter {
        if (parameters.size != 0) {
            writer.write("(")
            writer.write(parameters.joinToString(", "))
            writer.write(")")
        }

        return writer
    }
}