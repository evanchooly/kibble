package com.antwerkz.kibble

import com.intellij.lang.Language
import com.intellij.openapi.extensions.Extensions
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.impl.source.SourceTreeToPsiMap
import com.intellij.psi.impl.source.codeStyle.CodeFormatterFacade
import com.intellij.psi.impl.source.codeStyle.CodeStyleManagerImpl
import com.intellij.psi.impl.source.codeStyle.PostFormatProcessor
import org.jetbrains.kotlin.idea.KotlinLanguage
import org.jetbrains.kotlin.idea.KotlinLanguage.*
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
        if (level > 0) {
            for(i in 1..level) { writer.write("    ") }
        }
        writer.print(content)
    }

    fun writeln(content: String = "", level: Int = 0) {
        if (level > 0) {
            for(i in 1..level) { writer.write("    ") }
        }
        writer.println(content)
    }

    fun toFile(file: File) {
        file.writeText(toString())
    }

    fun toConsole() {
        println(toString())
    }

    fun format(): SourceWriter {
        val settings = CodeStyleSettings(false)
        val ktFile1 = Kibble.parseToKtFile(File(""))[0]
        val java = ktFile1::class.java
        dump(java)
        val ktFile = ktFile1 as com.intellij.psi.PsiElement

        val treeElement = SourceTreeToPsiMap.psiElementToTree(ktFile)
        var formatted = CodeFormatterFacade(settings, INSTANCE as com.intellij.lang.Language).processElement(treeElement).psi
        for (postFormatProcessor in Extensions.getExtensions(PostFormatProcessor.EP_NAME)) {
            formatted = postFormatProcessor.processElement(formatted, settings)
        }

        return this
    }

    private fun dump(java: Class<*>, level: Int = 0) {
        for(i in 1..level) { writer.write("    ") }
        val classes = mutableListOf<Class<*>>()
        var current: Class<*>? = java
        while (current != null) {
            classes.add(current)
            current = current.superclass
        }
        classes.reversed().forEach {
            for(i in 1..level) { print("    ") }
            println(it.name)
            for(i in 1..level) { print("    ") }
            println("-->")
            it.interfaces.forEach { int ->
                dump(int, level+1)
            }
        }
    }

    override fun toString(): String {
        return stringWriter.toString()
    }

}