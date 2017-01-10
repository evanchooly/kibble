package com.antwerkz.kibble

import com.antwerkz.kibble.model.KotlinFile
import org.antlr.v4.runtime.ANTLRFileStream
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.messages.MessageRenderer
import org.jetbrains.kotlin.cli.common.messages.MessageRenderer.PLAIN_FULL_PATHS
import org.jetbrains.kotlin.cli.common.messages.PrintingMessageCollector
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.lang.FileASTNode
import org.jetbrains.kotlin.com.intellij.openapi.Disposable
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CompilerConfigurationKey
import org.jetbrains.kotlin.config.addKotlinSourceRoot
import org.jetbrains.kotlin.psi.KtFile
import java.io.File

class Kibble {
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

        fun compile(path: String): List<KotlinFile> {
            val configuration = CompilerConfiguration()
            configuration.put(CompilerConfigurationKey.create<File>("output directory"), File(""))
            configuration.put(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY,
                    PrintingMessageCollector(System.err, PLAIN_FULL_PATHS, false))
            configuration.addKotlinSourceRoot(path)

            return KotlinCoreEnvironment
                    .createForProduction(Disposable { }, configuration, listOf())
                    .getSourceFiles()
                    .map { KotlinFile.evaluate(it) }
        }
    }
}
