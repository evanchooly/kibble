package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter

/**
 * Defines a secondary constructor for class
 *
 * @property delegationArguments the arguments to pass to the delegation constructor call
 */
class SecondaryConstructor internal constructor(vararg arguments: KibbleArgument) : Constructor() {

    val delegationArguments = mutableListOf<KibbleArgument>()

    init {
        delegationArguments += arguments
    }

    override fun toSource(writer: SourceWriter, level: Int): SourceWriter {
        writer {
            write("""constructor""", level)
            writeParameters(parameters)
            write(": this")
            writeArguments(delegationArguments)
            writeBlock(body, level)
            writer.writeln()
            writer.writeln()
        }

        return writer
    }
}