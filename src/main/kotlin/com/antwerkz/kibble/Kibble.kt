package com.antwerkz.kibble

import com.antwerkz.kibble.model.KibbleFile
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.messages.MessageRenderer.PLAIN_FULL_PATHS
import org.jetbrains.kotlin.cli.common.messages.PrintingMessageCollector
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles.JVM_CONFIG_FILES
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.com.intellij.openapi.Disposable
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CompilerConfigurationKey
import org.jetbrains.kotlin.config.addKotlinSourceRoot
import java.io.File

/**
 * This is the primary entry point for parsing existing Kotlin code
 */
object Kibble {
    /**
     * Parses a code snippet in to a Kibble model
     *
     * @return a KibbleFile holding the results of the parsed snippet
     */
    @JvmStatic
    @JvmOverloads
    fun parseSource(source: String, context: KibbleContext = KibbleContext()): KibbleFile {
        val tempFile = File.createTempFile("kibble-", ".kt")
        tempFile.deleteOnExit()

        try {
            tempFile.writeText(source)
            return parse(listOf(tempFile.absoluteFile), context)[0]
        } finally {
            tempFile.delete()
        }
    }

    /**
     * Parses source found at the given path.
     *
     * @return the KibbleFile
     */
    @JvmStatic
    @JvmOverloads
    fun parse(file: String, context: KibbleContext = KibbleContext()): KibbleFile {
        return parse(listOf(File(file)), context)[0]
    }

    /**
     * Parses sources found at the given path. This String can represent a source file or a directory to scan for sources
     *
     * @return the list of KibbleFiles from sources found at the given path
     */
    @JvmStatic
    @JvmOverloads
    fun parse(paths: List<File>, context: KibbleContext = KibbleContext()): List<KibbleFile> {
        val configuration = CompilerConfiguration()
        configuration.put(CompilerConfigurationKey.create<File>("output directory"), File(""))
        configuration.put(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY,
                PrintingMessageCollector(System.err, PLAIN_FULL_PATHS, false))
        paths.forEach { configuration.addKotlinSourceRoot(it.absolutePath) }

        val environment = KotlinCoreEnvironment.createForProduction(Disposable { }, configuration, JVM_CONFIG_FILES)

        val visitor = KibbleVisitor(context)
        environment.getSourceFiles()
                .forEach { it.accept(visitor) }

        return context.fileList()
    }
}
