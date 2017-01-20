package com.antwerkz.kibble

import com.antwerkz.kibble.model.KotlinFile
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.messages.MessageRenderer.PLAIN_FULL_PATHS
import org.jetbrains.kotlin.cli.common.messages.PrintingMessageCollector
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.com.intellij.openapi.Disposable
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CompilerConfigurationKey
import org.jetbrains.kotlin.config.addKotlinSourceRoot
import java.io.File

class Kibble {
    companion object {
        fun parse(path: String): List<KotlinFile> {
            val configuration = CompilerConfiguration()
            configuration.put(CompilerConfigurationKey.create<File>("output directory"), File(""))
            configuration.put(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY,
                    PrintingMessageCollector(System.err, PLAIN_FULL_PATHS, false))
            configuration.addKotlinSourceRoot(path)

            return KotlinCoreEnvironment
                    .createForProduction(Disposable { }, configuration, listOf())
                    .getSourceFiles()
                    .map(::KotlinFile)
        }

        fun  parseFunctionBody(body: String): String {
            val tempFile = File.createTempFile("kibble-", ".kt")
            tempFile.deleteOnExit()

            try {
                tempFile.writeText("fun temp() {\n\t$body\n}")
                val parse = parse(tempFile.absolutePath)[0]
                return parse.functions[0].body
            } finally {
                tempFile.delete()
            }
        }

    }
}
