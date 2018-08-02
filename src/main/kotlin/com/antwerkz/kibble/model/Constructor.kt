package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import com.antwerkz.kibble.model.Visibility.PUBLIC

/**
 * Defines a constructor for a Class
 *
 * @property visibility the constructor visibility
 * @property parameters the constructor parameters
 * @property body the constructor body
 */
class Constructor internal constructor() : Visible, ParameterHolder, KibbleElement {

    override var visibility = PUBLIC
    override var parameters = listOf<KibbleParameter>()
        private set

    override fun addParameter(parameter: KibbleParameter) {
        parameters += parameter
    }

    override fun toSource(writer: SourceWriter, level: Int): SourceWriter {
        TODO("should not be directly called")
    }

    override fun collectImports(file: KibbleFile) {
        parameters.forEach {
            it.type?.let { file.resolve(it) }
        }
    }
}