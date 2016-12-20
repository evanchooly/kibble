package com.antwerkz.kibble

import com.antwerkz.kibble.model.KotlinFile
import org.antlr.v4.runtime.ANTLRFileStream
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker
import java.io.File

class Kibble() {
    companion object {
        fun parse(source: File, enableLogging: Boolean = false): KotlinFile {
            val parser = KotlinParser(CommonTokenStream(KotlinLexer(ANTLRFileStream(source.absolutePath))))

            val listener = KotlinFileListener(enableLogging)
            ParseTreeWalker.DEFAULT.walk(listener, parser.kotlinFile())
            return listener.file
        }

        fun parse(source: String, enableLogging: Boolean = false): KotlinFile {
            val parser = KotlinParser(CommonTokenStream(KotlinLexer(ANTLRInputStream(source))))

            val listener = KotlinFileListener(enableLogging)
            ParseTreeWalker.DEFAULT.walk(listener, parser.kotlinFile())
            return listener.file
        }
    }
}
