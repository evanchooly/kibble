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
        writer.write("""constructor(${parameters.joinToString(", ")}): this(${delegationArguments.joinToString(", ")})""", level)
        body?.let {
            writer.writeln("{")
            val bodyText = it.trimIndent().split("\n")
            bodyText.forEach { writer.writeln(it, level + 1) }
            writer.write("}", level)
        }
        writer.writeln()
        writer.writeln()

        return writer

    }
}