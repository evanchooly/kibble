package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import com.antwerkz.kibble.model.Visibility.PUBLIC

/**
 * Defines a secondary constructor for class
 *
 * @property delegationArguments the arguments to pass to the delegation constructor call
 */
class SecondaryConstructor internal constructor(vararg arguments: KibbleArgument) : Visible, ParameterHolder, KibbleElement {
    override var visibility = PUBLIC
    override var parameters = listOf<KibbleParameter>()
        private set
    val delegationArguments = mutableListOf<KibbleArgument>()
    var body: String? = null

    init {
        delegationArguments += arguments
    }

    override fun addParameter(parameter: KibbleParameter) {
        parameters += parameter
    }

    override fun toSource(writer: SourceWriter, level: Int): SourceWriter {
        writer {
            write("""constructor""", level)
            writeParameters(listOf(), parameters)
            write(": this")
            writeArguments(delegationArguments)
            writeBlock(body, level)
            writer.writeln()
            writer.writeln()
        }

        return writer
    }

    override fun collectImports(file: KibbleFile) {
        parameters.forEach {
            it.type?.let { file.resolve(it) }
        }
    }

}