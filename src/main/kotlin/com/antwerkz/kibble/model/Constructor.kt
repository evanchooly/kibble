package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import com.antwerkz.kibble.model.Mutability.NEITHER
import com.antwerkz.kibble.model.Visibility.PUBLIC

/**
 * Defines a constructor for a Class
 *
 * @property visibility the constructor visibility
 * @property parameters the constructor parameters
 * @property body the constructor body
 */
open class Constructor internal constructor() : Visible, ParameterHolder, KibbleElement {

    override var visibility = PUBLIC
    override val parameters = mutableListOf<KibbleParameter>()
    var body: String? = null

    internal fun filterProperties(): List<KibbleProperty> {
        val groupBy = parameters.groupBy { it.mutability == NEITHER }
        parameters.clear()
        parameters += groupBy[true] ?: listOf()

        return groupBy[false]?.map {
            KibbleProperty(it.name ?: "", it.type, it.initializer, constructorParam = true).also { prop ->
                prop.annotations += it.annotations
                prop.mutability = it.mutability
                prop.typeParameters = it.typeParameters
            }
        } ?: listOf()
    }

    override fun toSource(writer: SourceWriter, level: Int): SourceWriter {
        if (parameters.size != 0) {
            writer.write("(")
            writer.write(parameters.joinToString(", "))
            writer.write(")")
        }

        return writer
    }

    override fun collectImports(file: KibbleFile) {
        parameters.forEach {
            it.type?.let { file.resolve(it) }
        }
    }
}