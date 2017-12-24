package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import org.jetbrains.kotlin.psi.KtSecondaryConstructor
import org.jetbrains.kotlin.psi.ValueArgument

/**
 * Defines a secondary constructor for class
 *
 * @property delegationArguments the arguments to pass to the delegation constructor call
 */
class SecondaryConstructor internal constructor(vararg arguments: String) : Constructor() {
    internal constructor(kt: KtSecondaryConstructor) : this() {
        kt.valueParameters.forEach {
            parameters.add(KibbleParameter(it))
        }
        body = kt.bodyExpression?.text
        val valueArguments: MutableList<out ValueArgument> = kt.getDelegationCall().valueArguments
        delegationArguments += valueArguments.map { it.getArgumentExpression()?.text ?: "" }
    }

    private val delegationArguments = mutableListOf<String>()

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