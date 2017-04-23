package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import org.jetbrains.kotlin.psi.KtSecondaryConstructor
import org.jetbrains.kotlin.psi.ValueArgument

/**
 * Defines a secondary constructor for class
 *
 * @property delegationArguments the arugments to pass to the delegation constructor call
 */
class SecondaryConstructor internal constructor() : Constructor() {
    internal constructor(klass: KibbleClass, kt: KtSecondaryConstructor) : this() {
        kt.valueParameters.forEach {
            this += KibbleParameter(klass.file, it)
        }
        body = kt.bodyExpression?.text ?: ""
        val valueArguments: MutableList<out ValueArgument> = kt.getDelegationCall().valueArguments
        delegationArguments += valueArguments.map { it.getArgumentExpression()?.text ?: "" }
    }

    val delegationArguments = mutableListOf<String>()

    override fun toSource(writer: SourceWriter, level: Int): SourceWriter {
        writer.write("constructor (", level)
        writer.write(parameters.joinToString(", "))
        writer.write(")")

        return writer

    }
}