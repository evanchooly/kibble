package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import com.antwerkz.kibble.model.Modality.FINAL
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
    : KibbleElement, TypeContainer, PropertyHolder, FunctionHolder, AnnotationHolder, Polymorphic,
        GenericCapable, Visible, Modal<KibbleClass> {

    var file: KibbleFile? = null

    val superCallArgs = mutableListOf<KibbleArgument>()
    var extends: KibbleType? = null
    val implements: MutableList<KibbleType> = mutableListOf()

    override var typeParameters = listOf<TypeParameter>()
        private set
    override var annotations = listOf<KibbleAnnotation>()
        private set
    override var classes = listOf<KibbleClass>()
        private set
    override var objects = listOf<KibbleObject>()
        private set
    override var functions = listOf<KibbleFunction>()
        private set
    override var properties = listOf<KibbleProperty>()
        private set

    var initBlock: InitBlock? = null

    var constructor = Constructor()
    var secondaries: List<SecondaryConstructor> = listOf()
        private set

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
            addSecondaryConstructor(it)
        }
    }

    fun addSecondaryConstructor(secondary: SecondaryConstructor) {
        secondaries += secondary
    }

    override fun addClass(name: String): KibbleClass {
        return KibbleClass(name).also {
            classes += it
        }
    }

    override fun addClass(klass: KibbleClass): KibbleClass {
        classes += klass
        klass.file = file
        return klass
    }

    fun classes() = classes

    /**
     * Adds (or gets if it already exists) a companion object to this class
     *
     * @return the companion object
     */
    fun addCompanionObject(): KibbleObject {
        return companion() ?: KibbleObject(companion = true).also {
            objects += it
            it.file = file
        }
    }

    override fun addObject(name: String, isCompanion: Boolean): KibbleObject {
        return addObject(KibbleObject(name, isCompanion))
    }

    override fun addObject(obj: KibbleObject): KibbleObject {
        objects += obj
        obj.file = file
        return obj
    }

    override fun addFunction(name: String?, type: String, body: String): KibbleFunction {
        return KibbleFunction(name = name, type = KibbleType.from(type), body = body).also {
            addFunction(it)
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
        writer {
            annotations.forEach { it.toSource(this, level) }
            writeIndent(level)
            write(visibility)
            write(modality)
            write(if (isInterface) "interface " else "class ")
            write(name)
            writeTypeParameters(typeParameters)

            val ctorProperties = properties.filter { it.constructorParam }.toMutableList()
            writeParameters(ctorProperties, constructor.parameters, false)

            writeParentCalls(extends, implements, superCallArgs)

            writeOpeningBrace()

            var previousWritten = writeCollection(false, secondaries, level + 1)

            initBlock?.toSource(writer, level + 1)

            val companions = objects.filter { it.companion }
            val bodyProperties = properties.filter { !it.constructorParam }
            val objects = objects.filter { !it.companion }
            val interfaces = classes.filter { it.isInterface }
            val nonInterfaces = classes.filter { !it.isInterface }

            previousWritten = previousWritten || initBlock != null
            writeCollections(previousWritten, level + 1, companions, bodyProperties, objects, interfaces, nonInterfaces, functions)

            writeln("}", level)
        }
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
        annotations.forEach { it.collectImports(file) }
        properties.forEach { it.collectImports(file) }
        classes.forEach { it.collectImports(file) }
        objects.forEach { it.collectImports(file) }
        functions.forEach { it.collectImports(file) }
        constructor.collectImports(file)
        secondaries.forEach { it.collectImports(file) }
        extends?.let { file.resolve(it) }
        implements.forEach { file.resolve(it) }
    }

    override fun addAnnotation(annotation: KibbleAnnotation) {
        annotations += annotation
    }

    override fun addFunction(function: KibbleFunction) {
        functions += function
    }

    override fun addProperty(property: KibbleProperty) {
        properties += property
    }

    override fun addTypeParameter(type: TypeParameter) {
        typeParameters += type
    }
}