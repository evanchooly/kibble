package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import org.jetbrains.kotlin.psi.KtFile
import java.io.File

class KibbleFile(val name: String? = null, override var pkgName: String? = null) :
        KibbleElement, FunctionHolder, PropertyHolder, Packaged, ClassOrObjectHolder {

    val imports = mutableSetOf<KibbleImport>()
    override val classes = mutableListOf<KibbleClass>()
    override val objects = mutableListOf<KibbleObject>()
    override val functions = mutableListOf<KibbleFunction>()
    override val properties = mutableListOf<KibbleProperty>()

    internal constructor(kt: KtFile) : this(kt.name, kt.packageDirective?.fqName.toString()) {
        sourcePath = kt.virtualFile.canonicalPath
        kt.importDirectives.forEach {
            imports += KibbleImport(it)
        }

        kt.declarations.let {
            extractClassesObjects(this, it)
            extractFunctions(this, it)
            extractProperties(this, it)
        }

        pkgName = kt.packageDirective?.children?.firstOrNull()?.text
    }

    var sourcePath: String? = null
        private set

    override fun addClass(name: String): KibbleClass {
        return KibbleClass(this, name).also {
            classes += it
        }
    }

    override fun addObject(name: String, isCompanion: Boolean): KibbleObject {
        return KibbleObject(this, name = name, companion = isCompanion).also {
            objects += it
        }

    }

    override fun addFunction(name: String?, type: String, body: String): KibbleFunction {
        return KibbleFunction(this, name, type = type, body = body).also {
            functions += it
        }
    }

    override fun addProperty(name: String, type: String?, initializer: String?, modality: Modality, overriding: Boolean,
                             visibility: Visibility, mutability: Mutability, lateInit: Boolean, constructorParam: Boolean): KibbleProperty {
        if (constructorParam) {
            throw IllegalArgumentException("File level properties can not also be constructor parameters")
        }
        val property = KibbleProperty(name, type?.let { KibbleType.from(type) }, initializer,
                modality, overriding, lateInit)

        property.visibility = visibility
        property.mutability = mutability
        properties += property
        return property
    }

    fun addImport(name: String, alias: String? = null): KibbleImport {
        return KibbleImport(KibbleType.from(name), alias).also {
            imports += it
        }
    }

    fun addImport(type: Class<*>, alias: String? = null): KibbleImport {
        return KibbleImport(KibbleType.from(type.name), alias).also {
            imports += it
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