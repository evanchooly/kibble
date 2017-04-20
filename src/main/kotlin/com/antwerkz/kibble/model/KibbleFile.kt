package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.KtImportDirective
import org.jetbrains.kotlin.psi.KtProperty
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File

class KibbleFile(val name: String? = null, override var pkgName: String? = null) :
        KibbleElement, FunctionHolder, PropertyHolder, Packaged {

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(KibbleFile::class.java)
    }

    val imports = mutableSetOf<KibbleImport>()
    val classes = mutableListOf<KibbleClass>()
    override val functions = mutableListOf<KibbleFunction>()
    override val properties = mutableListOf<KibbleProperty>()

    internal constructor(file: KtFile) : this(file.name, file.packageDirective?.fqName.toString()) {
        sourcePath = file.virtualFile.canonicalPath
        file.importDirectives.forEach {
            this += KibbleImport(it)
        }

        file.declarations.forEach {
            when (it) {
                is KtClass -> classes += KibbleClass(this, it)
                is KtFunction -> functions += KibbleFunction(this, it)
                is KtProperty -> properties += KibbleProperty(this, null, it)
                else -> LOG.warn("Unknown type being added to KotlinFile: $it")
            }
        }
        pkgName = file.packageDirective?.children?.firstOrNull()?.text
    }

    var sourcePath: String? = null
        private set

    fun addClass(name: String): KibbleClass {
        val klass = KibbleClass(this, name)
        classes += klass

        return klass
    }

    override fun addFunction(name: String?, type: String, body: String): KibbleFunction {
        val function = KibbleFunction(this, null, name, type = type, body = body)
        functions += function
        return function
    }

    fun addImport(name: String, alias: String? = null) {
        imports += KibbleImport(KibbleType.from(name), alias)
    }

    fun addImport(type: Class<*>, alias: String? = null) {
        imports += KibbleImport(KibbleType.from(type.name), alias)
    }

    override fun addProperty(name: String, type: String?, initializer: String?, modality: Modality, overriding: Boolean,
                             visibility: Visibility, mutability: Mutability, lateInit: Boolean, constructorParam: Boolean): KibbleProperty {
        if (constructorParam) {
            throw IllegalArgumentException("File level properties can not also be constructor parameters")
        }
        val property = KibbleProperty(this, null, name, type?.let { KibbleType.from(type) })
        property.visibility = visibility
        property.mutability = mutability
        properties += property
        return property
    }

    operator fun plusAssign(value: KibbleImport) {
        imports += value
    }

    fun outputFile(directory: File): File {
        var fileName = name
        pkgName?.let {
            fileName = it.replace('.', '/') + "/" + fileName
        }
        return File(directory, fileName)
    }


    override fun toSource(writer: SourceWriter, level: Int): SourceWriter {
        pkgName?.let {
            writer.writeln("package $it")
            writer.writeln()
        }

        writeBlock(writer, level, false, imports)
        writeBlock(writer, level, false, properties)
        writeBlock(writer, level, true, classes)
        writeBlock(writer, level, true, functions)

        return writer
    }

    private fun writeBlock(writer: SourceWriter, level: Int, inBetween: Boolean, block: Collection<KibbleElement>) {
        block.forEachIndexed { i, it ->
            if (inBetween && i != 0) {
                writer.writeln()
            }
            it.toSource(writer, level)
        }

        if (!inBetween && !block.isEmpty()) {
            writer.writeln()
        }
    }

    override fun toString(): String {
        return outputFile(File(".")).toString()
    }
}