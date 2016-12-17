package com.antwerkz.kibble

import com.antwerkz.kibble.model.KotlinFile
import org.antlr.v4.runtime.ANTLRFileStream
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker

class Kibble() {
    companion object {
        fun parse(vararg sources: String): List<KotlinFile> {
            return sources.map { source ->
                val parser = KotlinParser(CommonTokenStream(KotlinLexer(ANTLRFileStream(source))))

                val listener = KotlinFileListener()
                ParseTreeWalker.DEFAULT.walk(listener, parser.kotlinFile())
                listener.file
            }
        }
    }
}
