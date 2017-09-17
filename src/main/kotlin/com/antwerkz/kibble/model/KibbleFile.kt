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

    val imports = sortedSetOf<KibbleImport>()

    override val classes: MutableList<KibbleClass> by lazy {
        KibbleExtractor.extractClasses(kt?.declarations, file)
    }

    override val objects: MutableList<KibbleObject> by lazy {
        KibbleExtractor.extractObjects(file, kt?.declarations)
    }

    override val functions: MutableList<KibbleFunction> by lazy {
        KibbleExtractor.extractFunctions(file, kt?.declarations)
    }

    override val properties: MutableList<KibbleProperty> by lazy {
        KibbleExtractor.extractProperties(file, kt?.declarations)
    }

    override val file: KibbleFile = this
    var sourceTimestamp = 0L
        private set
    private var kt: KtFile? = null

    internal constructor(kt: KtFile, context: KibbleContext) : this(kt.name, kt.packageDirective?.fqName.toString(), context) {
        this.kt = kt
        pkgName = kt.packageDirective?.children?.firstOrNull()?.text
        sourceTimestamp = kt.originalFile.modificationStamp
        kt.importDirectives.forEach {
            imports += KibbleImport(this, it)
        }
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
        return KibbleFunction(this, name, type = type, body = body).also {
            functions += it
        }
    }

    override fun addProperty(name: String, type: String?, initializer: String?, modality: Modality, overriding: Boolean,
                             visibility: Visibility, mutability: Mutability, lateInit: Boolean, constructorParam: Boolean): KibbleProperty {
        if (constructorParam) {
            throw IllegalArgumentException("File level properties can not also be constructor parameters")
        }
        val property = KibbleProperty(this, name, type?.let { KibbleType.from(type) }, initializer,
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
    fun addImport(name: String, alias: String? = null) {
        addImport(KibbleType.from(name), alias)
    }

    /**
     * Adds an import to this file
     *
     * @param type the type to import
     * @param alias the alias of the import
     *
     * @return the new import
     */
    fun addImport(type: Class<*>, alias: String? = null) {
        addImport(KibbleType.from(type.name), alias)
    }

    private fun addImport(type: KibbleType, alias: String? = null) {
        type.pkgName?.let {
            imports.add(KibbleImport(KibbleType(type.className, type.pkgName, alias = alias)))
        }
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

    /**
     * @return the string form of this class
     */
    override fun toString(): String {
        return outputFile(File(".")).toString()
    }

/*
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
*/

    fun normalize(proposed: KibbleType): KibbleType {
        var normalized: KibbleType?

        val simpleMatch = imports.firstOrNull { proposed.className == it.type.alias || proposed.className == it.type.className }
        val fullMatch = imports.firstOrNull { proposed.pkgName == it.type.pkgName && proposed.className == it.type.className }

        val resolved = if (proposed.pkgName == null) simpleMatch else fullMatch

        if (resolved == null) {
            normalized = classes.filter { proposed.className == it.name }
                    .map {
                        KibbleType(proposed.className, file.pkgName, proposed.typeParameters, proposed.nullable, proposed.alias, true)
                    }
                    .firstOrNull() ?: context.resolve(this, proposed)
            if (normalized == null) {
                if (proposed.pkgName != null) {
                    addImport(proposed)
                    normalized = KibbleType(proposed.className, proposed.pkgName, proposed.typeParameters, proposed.nullable,
                            proposed.alias, true)
                }
            }
        } else {
            normalized = KibbleType(resolved.type.className, resolved.type.pkgName,
                    proposed.typeParameters, proposed.nullable, resolved.type.alias, true)
        }

        return normalized ?: proposed
    }

    /*
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
*/
}
