package com.antwerkz.kotlin

import com.antwerkz.kotlin.model.KotlinFile
import org.antlr.v4.runtime.ANTLRFileStream
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker

class Kibble() {
    companion object {
        fun parse(vararg sources: String): List<KotlinFile> {
            return sources.map { source ->
                val lexer = KotlinLexer(ANTLRFileStream(source))
                val tokens = CommonTokenStream(lexer)
                val parser = KotlinParser(tokens)

                val listener = KotlinFileListener()
                ParseTreeWalker.DEFAULT.walk(listener, parser.kotlinFile())
                listener.file
            }
        }
    }
}
