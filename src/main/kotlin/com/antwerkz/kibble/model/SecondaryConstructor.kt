package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import org.jetbrains.kotlin.psi.KtExpression
import org.jetbrains.kotlin.psi.KtSecondaryConstructor
import org.jetbrains.kotlin.psi.ValueArgument

class SecondaryConstructor internal constructor(klass: KibbleClass) : Constructor(klass.file, klass) {
    val delegationArguments = mutableListOf<String>()
    internal constructor(klass: KibbleClass, kt: KtSecondaryConstructor) : this(klass) {
        kt.valueParameters.forEach {
            this += KibbleParameter(file, it)
        }
        body = kt.bodyExpression?.text ?: ""
        val valueArguments: MutableList<out ValueArgument> = kt.getDelegationCall().valueArguments
        delegationArguments += valueArguments.map { it.getArgumentExpression()?.text ?: "" }
    }

    override fun toSource(writer: SourceWriter, level: Int): SourceWriter {
        writer.write("constructor (", level)
        writer.write(parameters.joinToString(", "))
        writer.write(")")

        return writer

    }
}