package com.antwerkz.kibble

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.KModifier.INTERNAL
import com.squareup.kotlinpoet.KModifier.PRIVATE
import com.squareup.kotlinpoet.KModifier.PROTECTED
import com.squareup.kotlinpoet.KModifier.PUBLIC
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.TypeSpec.Kind.Class
import com.squareup.kotlinpoet.TypeSpec.Kind.Interface
import com.squareup.kotlinpoet.TypeSpec.Kind.Object
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.messages.MessageRenderer.PLAIN_FULL_PATHS
import org.jetbrains.kotlin.cli.common.messages.PrintingMessageCollector
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles.JVM_CONFIG_FILES
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.com.intellij.openapi.Disposable
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CompilerConfigurationKey
import org.jetbrains.kotlin.config.addKotlinSourceRoot
import org.slf4j.LoggerFactory
import java.io.File

/**
 * This is the primary entry point for parsing existing Kotlin code
 */
object Kibble {
    private val LOG = LoggerFactory.getLogger(Kibble::class.java)

    /**
     * Parses a code snippet in to a Kibble model
     *
     * @return a KibbleFile holding the results of the parsed snippet
     */
    @JvmStatic
    @JvmOverloads
    fun parseSource(source: String, context: KibbleContext = KibbleContext()): FileSpec {
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
    fun parse(file: String, context: KibbleContext = KibbleContext()): FileSpec {
        return parse(listOf(File(file)), context)[0]
    }

    /**
     * Parses sources found at the given path. This String can represent a source file or a directory to scan for sources
     *
     * @return the list of KibbleFiles from sources found at the given path
     */
    @JvmStatic
    @JvmOverloads
    fun parse(paths: List<File>, context: KibbleContext = KibbleContext()): List<FileSpec> {
        val configuration = CompilerConfiguration()
        configuration.put(CompilerConfigurationKey.create<File>("output directory"), File(""))
        configuration.put(
                CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, PrintingMessageCollector(System.err, PLAIN_FULL_PATHS, false)
        )
        paths.forEach { configuration.addKotlinSourceRoot(it.absolutePath) }

        val environment = KotlinCoreEnvironment.createForProduction(Disposable { }, configuration, JVM_CONFIG_FILES)

        val visitor = KibbleVisitor(context)
        try {
            environment.getSourceFiles().forEach { it.accept(visitor) }
        } catch (e: NotImplementedError) {
            LOG.error(e.message, e)
            throw KibbleException("Failed to parse file:  ${e.message}")
        }

        return context.files
    }
}

val FileSpec.classes: List<TypeSpec>
    get() = members.filterIsInstance(TypeSpec::class.java).filter { it.kind is Class }
val FileSpec.interfaces: List<TypeSpec>
    get() = members.filterIsInstance(TypeSpec::class.java).filter { it.kind is Interface }
val FileSpec.objects: List<TypeSpec>
    get() = members.filterIsInstance(TypeSpec::class.java).filter { it.kind is Object }

val FileSpec.functions: List<FunSpec>
    get() = members.filterIsInstance(FunSpec::class.java)

val FileSpec.properties: List<PropertySpec>
    get() = members.filterIsInstance(PropertySpec::class.java)

val FunSpec.visibility: KModifier
    get() = modifiers.visibility

fun FileSpec.getClass(name: String): TypeSpec? = classes.firstOrNull { it.name == name }

fun FileSpec.getFunctions(name: String): List<FunSpec> = functions.filter { it.name == name }

val Set<KModifier>.visibility: KModifier
    get() = firstOrNull { it in setOf(PUBLIC, PROTECTED, PRIVATE, INTERNAL) } ?: PUBLIC

val TypeSpec.classes: List<TypeSpec>
    get() = typeSpecs.filter { it.kind is Class }
val TypeSpec.functions: List<FunSpec>
    get() = funSpecs
val TypeSpec.properties: List<PropertySpec>
    get() = properties
val TypeSpec.interfaces: List<TypeSpec>
    get() = typeSpecs.filter { it.kind is Interface }
val TypeSpec.objects: List<TypeSpec>
    get() = typeSpecs.filter { it.kind is Object }

fun TypeSpec.companion(): TypeSpec? {
    return typeSpecs.firstOrNull {
        it.isCompanion
    }
}

fun TypeSpec.getClass(name: String): TypeSpec? = typeSpecs.filter { it.kind is Class }.firstOrNull { it.name == name }
fun TypeSpec.getObject(name: String): TypeSpec? = typeSpecs.filter { it.kind is Object }.firstOrNull { it.name == name }
fun TypeSpec.getFunctions(name: String): List<FunSpec> = funSpecs.filter { it.name == name }
fun TypeSpec.getProperty(name: String): PropertySpec? = propertySpecs.firstOrNull { it.name == name }
fun TypeSpec.isAbstract() = KModifier.ABSTRACT in modifiers
val TypeSpec.visibility: KModifier
    get() = modifiers.visibility

val TypeSpec.secondaries: List<FunSpec>
    get() = funSpecs.filter {
        it.isConstructor
    }