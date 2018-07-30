package com.antwerkz.kibble

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

    internal fun write(content: String, level: Int = 0) {
        (1..level).forEach { writer.write("    ") }
        writer.print(content)
    }

    internal fun writeln(content: String = "", level: Int = 0) {
        (1..level).forEach { writer.write("    ") }
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

}