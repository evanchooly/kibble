package com.antwerkz.kibble

import java.io.Closeable
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.io.StringWriter

abstract class SourceWriter(val writer: PrintWriter): Closeable {

     fun writeIndent(count: Int) {
        (1..count).forEach { writer.write("    ") }
     }

     fun write(content: String) {
        writer.print(content)
    }

     fun writeln(content: String = "") {
        writer.println(content)
    }

}

class FileSourceWriter(file: File) : SourceWriter(PrintWriter(FileOutputStream(file))) {
    override fun close() {
        writer.flush()
        writer.close()
    }
}

class ConsoleSourceWriter: SourceWriter(PrintWriter(System.out)) {
    override fun close() {
        writer.flush()
    }
}

class StringSourceWriter(val stringWriter: StringWriter = StringWriter()): SourceWriter(PrintWriter(stringWriter)) {
    override fun close() {
    }

    override fun toString(): String {
        return stringWriter.toString()
    }


}