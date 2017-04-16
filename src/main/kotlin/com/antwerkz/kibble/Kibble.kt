package com.antwerkz.kibble

import com.antwerkz.kibble.model.KibbleFile
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.messages.MessageRenderer.PLAIN_FULL_PATHS
import org.jetbrains.kotlin.cli.common.messages.PrintingMessageCollector
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.com.intellij.openapi.Disposable
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CompilerConfigurationKey
import org.jetbrains.kotlin.config.addKotlinSourceRoot
import org.jetbrains.kotlin.psi.KtFile
import java.io.File

class Kibble {
    companion object {
        @JvmStatic
        fun parseFile(file: String): KibbleFile {
            return parse(File(file))[0]
        }

        @JvmStatic
        fun parseFile(file: File): KibbleFile {
            return parse(file.absoluteFile)[0]
        }

        @JvmStatic
        fun parse(path: File): List<KibbleFile> {
            return parseToKtFile(path)
                    .map(::KibbleFile)
        }

        internal fun parseToKtFile(path: File): List<KtFile> {
            val configuration = CompilerConfiguration()
            configuration.put(CompilerConfigurationKey.create<File>("output directory"), File(""))
            configuration.put(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY,
                    PrintingMessageCollector(System.err, PLAIN_FULL_PATHS, false))
            configuration.addKotlinSourceRoot(path.absolutePath)

            val sourceFiles = KotlinCoreEnvironment
                    .createForProduction(Disposable { }, configuration, listOf())
                    .getSourceFiles()
            return sourceFiles
        }

        @JvmStatic
        fun parseSource(source: String): KibbleFile {
            val tempFile = File.createTempFile("kibble-", ".kt")
            tempFile.deleteOnExit()

            try {
                tempFile.writeText(source)
                return parse(tempFile.absoluteFile)[0]
            } finally {
                tempFile.delete()
            }
        }

    }
}
