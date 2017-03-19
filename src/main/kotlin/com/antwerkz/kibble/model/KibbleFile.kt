package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.KtProperty
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File

class KibbleFile(val name: String? = null, var pkgName: String? = null) :
        KibbleElement, FunctionHolder, PropertyHolder, Packaged<KibbleFile> {

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(KibbleFile::class.java)
    }

    private val imports = mutableSetOf<KibbleImport>()
    private val classes = mutableListOf<KibbleClass>()
    override val functions = mutableListOf<KibbleFunction>()
    override val properties = mutableListOf<KibbleProperty>()

    internal constructor(file: KtFile) : this(file.name, file.packageDirective?.fqName.toString()) {
        sourcePath = file.virtualFile.canonicalPath
        file.declarations.forEach {
            when (it) {
                is KtClass -> classes += KibbleClass(this, it)
                is KtFunction -> functions += KibbleFunction(this, it)
                is KtProperty -> properties += KibbleProperty(this, it)
                else -> LOG.warn("Unknown type being added to KotlinFile: $it")
            }
        }
        file.importDirectives.forEach {
            this += KibbleImport(it)
        }
        pkgName = file.packageDirective?.children?.firstOrNull()?.text
    }

    override var parentClass: KibbleClass? = null
    var sourcePath: String? = null
        private set

    override var kibbleFile = this

    fun addClass(name: String): KibbleClass {
        val klass = KibbleClass(this, name)
        classes += klass

        return klass
    }

    override fun addProperty(name: String, type: String): KibbleProperty {
        val property = KibbleProperty(this, name, KibbleType.from(type))
        properties += property
        return property
    }

    override fun addFunction(name: String?, type: String, body: String): KibbleFunction {
        val function = KibbleFunction(this, name, type = type, body = body)
        functions += function
        return function
    }

    operator fun plusAssign(value: KibbleImport) {
        if (value.name.contains(".")) {
            imports += value
        }
    }

    fun outputFile(directory: File): File {
        var fileName = name + ".kt"
        pkgName?.let {
            fileName = it.replace('.', '/') + "/" + fileName
        }
        return File(directory, fileName)
    }


    override fun toSource(writer: SourceWriter, indentationLevel: Int) {
        pkgName?.let {
            writer.writeln("package $it")
            writer.writeln()
        }

        imports.forEach { it.toSource(writer, indentationLevel) }
        properties.forEach { it.toSource(writer, indentationLevel) }
        classes.forEach{ it.toSource(writer, indentationLevel) }
        functions.forEach { it.toSource(writer, indentationLevel) }
    }

    override fun toString(): String {
        return outputFile(File(".")).toString()
    }
}