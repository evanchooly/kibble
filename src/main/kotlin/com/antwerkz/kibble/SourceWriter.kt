package com.antwerkz.kibble

import com.antwerkz.kibble.model.KibbleArgument
import com.antwerkz.kibble.model.KibbleParameter
import com.antwerkz.kibble.model.KibbleType
import com.antwerkz.kibble.model.Modality
import com.antwerkz.kibble.model.Modality.FINAL
import com.antwerkz.kibble.model.Mutability
import com.antwerkz.kibble.model.Mutability.VAL
import com.antwerkz.kibble.model.Mutability.VAR
import com.antwerkz.kibble.model.TypeParameter
import com.antwerkz.kibble.model.Visibility
import com.antwerkz.kibble.model.Visibility.NONE
import com.antwerkz.kibble.model.Visibility.PUBLIC
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter

/**
 * This is used to convert a Kibble model to its string form.
 *
 * @property
 */
class SourceWriter {
    private val stringWriter = StringWriter()
    private val writer = PrintWriter(stringWriter)

    private val indent = "    "

    internal fun write(content: String, level: Int = 0) {
        (1..level).forEach { writer.write(indent) }
        writer.print(content)
    }

    internal fun writeln(content: String = "", level: Int = 0) {
        (1..level).forEach { writer.write(indent) }
        writer.println(content)
    }

    /**
     * Dumps the content of this SourceWriter to a file
     *
     * @param file the file to use
     */
    fun toFile(file: File) {
        file.writeText(toString())
    }

    /**
     * Returns the source written to this SourceWriter
     *
     * @return the source
     */
    override fun toString(): String {
        return stringWriter.toString()
    }

    fun write(visibility: Visibility?) {
        when (visibility) {
            NONE, PUBLIC, null -> {
            }
            else -> {
                write(visibility.toString())
                write(" ")
            }
        }
    }

    fun write(modality: Modality?) {
        when (modality) {
            FINAL, null -> {
            }
            else -> {
                write(modality.toString())
                write(" ")
            }
        }
    }

    fun write(mutability: Mutability?) {
        when (mutability) {
            null -> {
            }
            VAL, VAR -> {
                write(mutability.toString())
                write(" ")
            }
        }
    }

    operator fun invoke(func: SourceWriter.() -> Unit): SourceWriter {
        func()
        return this
    }

    fun writeParameters(parameters: List<KibbleParameter>) {
        write(parameters.joinToString(", ", "(", ")", transform = { it.toSource().toString() }))
    }

    fun writeType(type: KibbleType?) {
        write(if (type?.toString() != "Unit") ": $type" else "")
    }

    fun writeTypeParameters(typeParameters: List<TypeParameter>) {
        if (typeParameters.isNotEmpty()) {
            write(typeParameters.joinToString(prefix = "<", postfix = "> "))
        }
    }

    fun writeBlock(body: String?, level: Int) {
        if (body != null && body.isNotBlank()) {
            writeln(" {")
            writeln(body.prependIndent(indent * (level + 1)))
            writeln("}", level)
        }
    }

    operator fun String.times(count: Int): String {
        var result = ""
        (1..count).forEach { result += this }

        return result
    }

    fun writeArguments(arguments: List<KibbleArgument>) {
        write(arguments.joinToString(", ", prefix = "(", postfix = ")", transform = { it.toSource().toString() }))
    }

    fun writeSuperCall(extends: KibbleType?, args: List<KibbleArgument>) {
        extends?.let {
            it.toSource(this)
            writeArguments(args)
        }
    }

    fun writeInitializer(initializer: String?) {
        initializer?.let { writer.write(" = $it") }
    }

}