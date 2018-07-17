package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import com.antwerkz.kibble.model.Modality.FINAL
import com.antwerkz.kibble.model.Mutability.NEITHER
import com.antwerkz.kibble.model.Visibility.PUBLIC

/**
 * Represents an annotation in kotlin source code.
 *
 * @property name the class name
 * @property initBlock any custom init block for this class
 * @property constructor the primary constructor for this class
 * @property secondaries the secondary constructors this class
 */
class KibbleClass internal constructor(var name: String = "",
                                       override var modality: Modality = FINAL,
                                       override var visibility: Visibility = PUBLIC)
    : KibbleElement, ClassOrObjectHolder, PropertyHolder, FunctionHolder, AnnotationHolder, Polymorphic,
        GenericCapable, Visible, Modal<KibbleClass> {

    val superCallArgs = mutableListOf<KibbleArgument>()
    var extends: KibbleType? = null
    val implements: MutableList<KibbleType> = mutableListOf()

    override val typeParameters = mutableListOf<TypeParameter>()
    override val annotations = mutableListOf<KibbleAnnotation>()
    override val classes = mutableListOf<KibbleClass>()
    override val objects = mutableListOf<KibbleObject>()
    override val functions = mutableListOf<KibbleFunction>()
    override val properties = mutableListOf<KibbleProperty>()

    var initBlock: InitBlock? = null

    var constructor = Constructor()
    val secondaries: MutableList<SecondaryConstructor> = mutableListOf()

    var enum = false
    var data = false
    var sealed = false
    var isInterface = false

    override fun extends(type: KibbleType, arguments: List<KibbleArgument>) {
        extends = type
        superCallArgs += arguments
    }

    override fun implements(type: KibbleType) {
        implements += type
    }

    /**
     * Adds a secondary constructor to this class
     *
     * @return the new constructor
     */
    fun addSecondaryConstructor(vararg arguments: String): SecondaryConstructor {
        return SecondaryConstructor(*(arguments
                .map { KibbleArgument(value = it) }
                .toTypedArray())).also {
            secondaries += it
        }
    }

    override fun addClass(name: String): KibbleClass {
        return KibbleClass(name).also {
            classes += it
        }
    }

    /**
     * Adds (or gets if it already exists) a companion object to this class
     *
     * @return the companion object
     */
    fun addCompanionObject(): KibbleObject {
        return companion() ?: KibbleObject(companion = true).also {
            objects.add(it)
        }
    }

    override fun addObject(name: String, isCompanion: Boolean): KibbleObject {
        return KibbleObject(name, isCompanion).also {
            objects += it
        }
    }

    override fun addFunction(name: String?, type: String, body: String): KibbleFunction {
        return KibbleFunction(name = name, type = KibbleType.from(type), body = body).also {
            functions += it
        }
    }

    fun isEnum() = enum

    /**
     * @return the string form of this class
     */
    override fun toString(): String {
        return (if (isInterface) "interface" else "class") + " $name"
    }

    override fun toSource(writer: SourceWriter, level: Int): SourceWriter {
        annotations.forEach { writer.writeln(it.toString(), level) }
        writer.write("$visibility${modality}", level)
        writer.write(if (isInterface) "interface " else "class ")
        writer.write(name)
        if (!typeParameters.isEmpty()) {
            writer.write(typeParameters.joinToString(", ", prefix = "<", postfix = ">"))
        }

        val ctorParams: MutableList<KibbleParameter> = properties.filter { it.constructorParam }.toMutableList()
        ctorParams.addAll(constructor.parameters)
        if (ctorParams.size != 0) {
            writer.write("(")
            writer.write(ctorParams.joinToString(", "))
            writer.write(")")
        }

        val extends = mutableListOf<String>()
        this.extends?.let {
            extends += "$it${superCallArgs.joinToString(prefix = "(", postfix = ")")}"
        }
        if (!implements.isEmpty()) {
            extends += implements.joinToString(", ")
        }
        if (extends.isNotEmpty()) {
            writer.write(": ")
            writer.write(extends.joinToString(", "))
        }

        writer.writeln(" {")

        objects.filter { it.companion }
                .forEach { it.toSource(writer, level + 1) }

        secondaries.forEach { it.toSource(writer, level + 1) }
        initBlock?.let {
            it.toSource(writer, level + 1)
            writer.writeln()
        }
        properties.filter { !it.constructorParam }
                .forEach { it.toSource(writer, level + 1) }

        objects.filter { !it.companion }
                .forEach { it.toSource(writer, level + 1) }
        classes.filter { it.isInterface }
                .forEach { it.toSource(writer, level + 1) }
        classes.filter { !it.isInterface }
                .forEach { it.toSource(writer, level + 1) }
        functions.forEach { it.toSource(writer, level + 1) }

        writer.write("}", level)

        writer.writeln()
        return writer
    }

    /**
     * Gets the companion object if it exists
     *
     * @return the companion object
     */
    fun companion(): KibbleObject? {
        return objects.firstOrNull { it.companion }
    }

    override fun collectImports(file: KibbleFile) {
        properties.forEach { it.collectImports(file) }
        classes.forEach { it.collectImports(file) }
        objects.forEach { it.collectImports(file) }
        functions.forEach { it.collectImports(file) }
        constructor.collectImports(file)
        secondaries.forEach { it.collectImports(file) }
        extends?.let { file.resolve(it) }
        implements.forEach { file.resolve(it) }
    }
}