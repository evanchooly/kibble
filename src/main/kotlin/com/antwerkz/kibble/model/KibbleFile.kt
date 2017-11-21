package com.antwerkz.kibble.model

import com.antwerkz.kibble.KibbleContext
import com.antwerkz.kibble.SourceWriter
import org.jetbrains.kotlin.psi.KtFile
import java.io.File
import kotlin.Long.Companion

/**
 * Defines a kotlin source file model
 *
 * @property name the name of the physical kotlin file
 * @property pkgName the package name
 * @property imports the imports defined in the file
 */
class KibbleFile(val name: String? = null, var pkgName: String? = null,
                 val context: KibbleContext = KibbleContext()) :
        KibbleElement, PropertyHolder, ClassOrObjectHolder {

    val imports = sortedSetOf<KibbleImport>()

    override val classes: MutableList<KibbleClass> by lazy {
        KibbleExtractor.extractClasses(this, kt?.declarations)
    }

    override val objects: MutableList<KibbleObject> by lazy {
        KibbleExtractor.extractObjects(this, kt?.declarations)
    }

    override val functions: MutableList<KibbleFunction> by lazy {
        KibbleExtractor.extractFunctions(kt?.declarations)
    }

    override val properties: MutableList<KibbleProperty> by lazy {
        KibbleExtractor.extractProperties(kt?.declarations)
    }

    private var kt: KtFile? = null
    val sourceTimestamp = kt?.originalFile?.modificationStamp ?: Long.MIN_VALUE

    internal constructor(kt: KtFile, context: KibbleContext) : this(kt.name, kt.packageDirective?.fqName.toString(), context) {
        this.kt = kt
        kt.virtualFile.modificationStamp
        pkgName = kt.extractPackage()
        kt.importDirectives.forEach {
            imports += KibbleImport(it)
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
        return KibbleObject(this, name, isCompanion).also {
            objects += it
        }

    }

    override fun addFunction(name: String?, type: String, body: String): KibbleFunction {
        return KibbleFunction(name, type = KibbleType.from(type), body = body).also {
            functions += it
        }
    }

    override fun addProperty(name: String, type: String?, initializer: String?, modality: Modality, overriding: Boolean,
                             visibility: Visibility, mutability: Mutability, lateInit: Boolean, constructorParam: Boolean): KibbleProperty {
        if (constructorParam) {
            throw IllegalArgumentException("File level properties can not also be constructor parameters")
        }
        val property = KibbleProperty(name, type?.let { KibbleType.from(type) }, initializer, modality, overriding, lateInit)

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
        if (type.pkgName != null) {
            KibbleImport(type, alias).also {
                imports.add(it)
                type.resolved = alias ?: type.className
            }
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

        collectImports(properties, objects, classes, functions)
        writeBlock(writer, level, false, imports)
        writeBlock(writer, level, false, properties)
        writeBlock(writer, level, true, classes)
        writeBlock(writer, level, true, functions)

        return writer
    }

    private fun collectImports(vararg list: MutableList<out KibbleElement>) {
        list.flatMap { it }
                .forEach { it.collectImports(this) }
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

    override fun collectImports(file: KibbleFile) {
        collectImports(file, properties, classes, objects, functions)
    }

    private fun collectImports(file: KibbleFile, vararg list: MutableList<out KibbleElement>) {
        list.flatMap { it }
                .forEach { it.collectImports(file) }
    }

    /**
     * @return the string form of this class
     */
    override fun toString(): String {
        return outputFile(File(".")).toString()
    }

    fun resolve(type: KibbleType): KibbleType {
        val imported = imports.firstOrNull {
            type.fqcn == it.type.fqcn || type.className == it.type.className || type.className == it.alias
        }
        if (imported == null) {
            if (type.pkgName != pkgName) {
                addImport(type)
            } else {
                type.resolved = type.className
            }
        } else if (imported.type.fqcn == type.fqcn) {
            type.resolved = imported.alias ?: type.className
        } else if (imported.alias == type.className) {
            type.resolved = type.className
        } else if (imported.type.className == type.className && type.pkgName == null) {
            type.pkgName = imported.type.pkgName
        } else {
            throw IllegalStateException("what got me here? type = $type,  import = $imported")
        }

        type.typeParameters.forEach {
            resolve(it.type)
        }
        return type
    }

/*
    fun normalize(proposed: KibbleType): KibbleType {
        var normalized: KibbleType?

        val simpleMatch = imports.firstOrNull { proposed.className == it.alias || proposed.className == it.type.className }
        val fullMatch = imports.firstOrNull { proposed.pkgName == it.type.pkgName && proposed.className == it.type.className }

        val resolved = if (proposed.pkgName == null) simpleMatch else fullMatch

        if (resolved == null) {
            normalized = classes.filter { proposed.className == it.name }
                    .map {
                        KibbleType(proposed.className, pkgName, proposed.typeParameters, proposed.nullable, true)
                    }
                    .firstOrNull() ?: context.resolve(this, proposed)
            if (normalized == null) {
                if (proposed.pkgName != null) {
                    addImport(proposed)
                    normalized = KibbleType(proposed.className, proposed.pkgName, proposed.typeParameters, proposed.nullable, true)
                }
            }
        } else {
            normalized = KibbleType(resolved.type.className, resolved.type.pkgName,
                    proposed.typeParameters, proposed.nullable, true)
        }

        return normalized ?: proposed
    }
*/

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
