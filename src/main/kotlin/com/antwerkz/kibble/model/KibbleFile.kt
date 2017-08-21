package com.antwerkz.kibble.model

import com.antwerkz.kibble.KibbleContext
import com.antwerkz.kibble.SourceWriter
import org.jetbrains.kotlin.psi.KtFile
import java.io.File

/**
 * Defines a kotlin source file model
 *
 * @property name the name of the physical kotlin file
 * @property pkgName the package name
 * @property imports the imports defined in the file
 */
class KibbleFile(val name: String? = null, override var pkgName: String? = null,
                 val context: KibbleContext = KibbleContext()) :
        KibbleElement, PropertyHolder, Packaged, ClassOrObjectHolder {

    val imports = mutableSetOf<KibbleImport>()
    override val classes = mutableListOf<KibbleClass>()
    override val objects = mutableListOf<KibbleObject>()
    override val functions = mutableListOf<KibbleFunction>()
    override val properties = mutableListOf<KibbleProperty>()
    var sourceTimestamp = 0L
        private set

    internal constructor(kt: KtFile, context: KibbleContext) : this(kt.name, kt.packageDirective?.fqName.toString(), context) {
        sourceTimestamp = kt.originalFile.modificationStamp
        kt.importDirectives.forEach {
            imports += KibbleImport(it)
        }

        kt.declarations.let {
            extractClassesObjects(this, it)
            extractFunctions(it)
            extractProperties(this, it)
        }

        pkgName = kt.packageDirective?.children?.firstOrNull()?.text
    }

    init {
        context.register(this)
    }

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
        return KibbleFunction(name, type = type, body = body).also {
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

    /**
     * Adds an import to this file
     *
     * @param name the type to import
     * @param alias the alias of the import
     *
     * @return the new import
     */
    fun addImport(name: String, alias: String? = null): KibbleImport? {
        return addImport(KibbleType.from(name), alias)
    }

    /**
     * Adds an import to this file
     *
     * @param type the type to import
     * @param alias the alias of the import
     *
     * @return the new import
     */
    fun addImport(type: Class<*>, alias: String? = null): KibbleImport? {
        return addImport(KibbleType.from(type.name), alias)
    }

    private fun addImport(type: KibbleType, alias: String?): KibbleImport? {
        val kibbleImport = KibbleImport(type, alias)
        return if(imports.add(kibbleImport)) kibbleImport else null
    }

    /**
     * Creates a File reference for this KibbleFile based on the directory given.
     *
     * @param directory the output directory
     *
     * @return the new File
     */
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

        writeBlock(writer, level, false, imports.sortedBy { it.type.fqcn })
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

    /**
     * @return the string form of this class
     */
    override fun toString(): String {
        return outputFile(File(".")).toString()
    }

    fun resolve(type: KibbleType): KibbleType {
        var resolved: KibbleType? = imports.firstOrNull { type == it.type }?.type

        if (resolved == null) {
            resolved = imports.firstOrNull { it.type.className == type.fqcn || it.alias == name }?.type
        }

        if (resolved == null) {
            classes.firstOrNull { type.className == it.name }?.let {
                resolved = KibbleType.resolve(type, pkgName)
            }
        }
        if (resolved == null) {
            resolved = context.resolve(this, type)
        }

        return resolved ?: type
    }

    fun resolve(type: String): KibbleType {
        var resolved: KibbleType? = imports.firstOrNull { it.type.fqcn == type || it.alias == name }?.type

        if (resolved == null) {
            classes.firstOrNull { type == it.name }?.let {
                resolved = KibbleType("$pkgName.$type")
            }
        }
        if (resolved == null) {
            resolved = context.resolve(this, type)
        }

        return resolved ?: KibbleType(type)
    }
}
