package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtFunction
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
        file.declarations.forEach {
            when (it) {
                is KtClass -> classes += KibbleClass(this, it)
                is KtFunction -> functions += KibbleFunction(this, it)
                is KtProperty -> properties += KibbleProperty(this, null, it)
                else -> LOG.warn("Unknown type being added to KotlinFile: $it")
            }
        }
        file.importDirectives.forEach {
            this += KibbleImport(it)
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
        imports += KibbleImport(name, alias)
    }

    fun addImport(type: Class<*>, alias: String? = null) {
        imports += KibbleImport(type, alias)
    }

    override fun addProperty(name: String, type: String, initializer: String?, modality: Modality, overriding: Boolean,
                             visibility: Visibility, mutability: Mutability, lateInit: Boolean, constructorParam:Boolean): KibbleProperty {
        if (constructorParam) {
            throw IllegalArgumentException("File level properties can not also be constructor parameters")
        }
        val property = KibbleProperty(this, null, name, KibbleType.from(type))
        property.visibility = visibility
        property.mutability = mutability
        properties += property
        return property
    }

    operator fun plusAssign(value: KibbleImport) {
        if (value.name.contains(".")) {
            imports += value
        }
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

        imports.forEach { it.toSource(writer, level) }
        properties.forEach { it.toSource(writer, level) }
        classes.forEach { it.toSource(writer, level) }
        functions.forEach { it.toSource(writer, level) }
        return writer
    }

    override fun toString(): String {
        return outputFile(File(".")).toString()
    }
}