package com.antwerkz.kibble

import java.io.File
import java.io.PrintWriter
import java.io.StringWriter

class SourceWriter {
    val stringWriter = StringWriter()
    val writer = PrintWriter(stringWriter)

    @Deprecated("use the write() methods")
    fun writeIndent(count: Int) {
        (1..count).forEach { writer.write("    ") }
    }

    fun write(content: String, level: Int = 0) {
        (1..level).forEach { writer.write("    ") }
        writer.print(content)
    }

    fun writeln(content: String = "", level: Int = 0) {
        (1..level).forEach { writer.write("    ") }
        writer.println(content)
    }

    fun toFile(file: File) {
        file.writeText(toString())
    }

    fun toConsole() {
        println(toString())
    }

    override fun toString(): String {
        return stringWriter.toString()
    }

}