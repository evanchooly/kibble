package com.antwerkz.kibble.model

import com.antwerkz.kibble.KibbleContext
import com.antwerkz.kibble.SourceWriter
import java.io.File


/**
 * Defines a kotlin source file model
 *
 * @property name the name of the physical kotlin file
 * @property pkgName the package name
 * @property importDirectives the imports defined in the file
 */
class KibbleFile(val name: String? = null, var pkgName: String? = null,
                 val context: KibbleContext = KibbleContext()) :
        KibbleElement, TypeContainer, PropertyHolder, FunctionHolder {

    private val importDirectives = sortedMapOf<String, KibbleImport>()
    val imports
        get() = importDirectives.values.toSet()


    override var classes = listOf<KibbleClass>()
        private set
    override var objects = listOf<KibbleObject>()
        private set
    override var functions = listOf<KibbleFunction>()
        private set
    override var properties = listOf<KibbleProperty>()
        private set

    var sourceTimestamp: Long = 0

    init {
        context.register(this)
    }

    override fun addClass(name: String): KibbleClass {
        return addClass(KibbleClass(name))
    }

    override fun addClass(klass: KibbleClass): KibbleClass {
        classes += klass
        klass.file = this
        return klass
    }

    override fun addObject(name: String, isCompanion: Boolean): KibbleObject {
        return addObject(KibbleObject(name, isCompanion))
    }

    override fun addObject(obj: KibbleObject): KibbleObject {
        objects += obj
        obj.file = this
        return obj
    }

    override fun addFunction(name: String?, type: String, body: String): KibbleFunction {
        return KibbleFunction(name, type = KibbleType.from(type), body = body).also {
            functions += it
        }
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

    fun addImport(type: KibbleType, alias: String? = null) {
        if (type.pkgName != null || type.className.endsWith(".*")) {
            addImport(KibbleImport(type, alias))
        }
    }

    fun addImport(kibbleImport: KibbleImport) {
        if (importDirectives[kibbleImport.type.fqcn()] == null) {
            importDirectives[kibbleImport.type.fqcn()] = kibbleImport
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

    fun collectImports() {
        properties.forEach { it.collectImports(this) }
        objects.forEach { it.collectImports(this) }
        classes.forEach { it.collectImports(this) }
        functions.forEach { it.collectImports(this) }
    }

    override fun toSource(writer: SourceWriter, level: Int): SourceWriter {
        collectImports()

        val interfaces = classes.filter { it.isInterface }
        val classes = classes.filter { !it.isInterface }

        writer {
            writePackage(pkgName)

            var previousWritten = writeCollection(pkgName?.isNotEmpty() ?: false, imports, level, false)

            writeCollections(previousWritten, level, properties, interfaces, classes, objects, functions)
        }

        return writer
    }

    override fun collectImports(file: KibbleFile) {
        properties.forEach { it.collectImports(file) }
        classes.forEach { it.collectImports(file) }
        objects.forEach { it.collectImports(file) }
        functions.forEach { it.collectImports(file) }
    }

    /**
     * @return the string form of this class
     */
    override fun toString(): String {
        return outputFile(File(".")).toString()
    }

    fun resolve(type: KibbleType): KibbleType {
        val imported = importDirectives[type.fqcn()] ?: importDirectives
                .filterValues { it.alias == type.className || it.type.className == type.className }
                .map { it.value }
                .firstOrNull()
        when {
            imported != null -> {
                if (type.pkgName == null) {
                    type.pkgName = imported.type.pkgName
                }
                type.resolvedName = imported.alias ?: imported.type.resolvedName
            }

            else -> when {
                type.pkgName == null /*&& classes.any { it.name == type.className }*/ -> {
                    if (!type.isAutoImported()) {
                        type.pkgName = pkgName
                    }
                    type.resolvedName = type.className
                }
                type.pkgName != pkgName -> addImport(type)
                else -> type.resolvedName = type.className
            }
        }

        type.typeParameters.forEach {
            it.type?.let { resolve(it) }
        }
        return type
    }

    override fun addFunction(function: KibbleFunction) {
        functions += function
    }

    override fun addProperty(property: KibbleProperty) {
        properties += property
    }
}
