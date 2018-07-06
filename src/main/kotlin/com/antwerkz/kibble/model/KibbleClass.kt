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
    : KibbleElement, ClassOrObjectHolder, PropertyHolder, FunctionHolder, AnnotationHolder, Polymorphic,
        GenericCapable, Visible, Modal<KibbleClass> {

    val superCallArgs = mutableListOf<KibbleArgument>()
    var superTypes = mutableListOf<KibbleType>()
    var parentClass: KibbleType? = null
//    val parentInterfaces: MutableList<KibbleType>

    override val typeParameters = mutableListOf<TypeParameter>()
    override val annotations = mutableListOf<KibbleAnnotation>()
    override val classes = mutableListOf<KibbleClass>()
    override val objects = mutableListOf<KibbleObject>()
    override val functions = mutableListOf<KibbleFunction>()
    override val properties = mutableListOf<KibbleProperty>()
    val implements: MutableList<KibbleType> = mutableListOf()

    var initBlock: String? = null

    var constructor = Constructor()
    val secondaries: MutableList<SecondaryConstructor> = mutableListOf()

    var enum = false
    var isInterface = false

/*
    internal constructor(kt: KtClass) : this(*/
/*file,*//*
 kt.name ?: "") {
        modality = kt.modalityModifier().toModality()
        visibility = Visible.apply(kt.visibilityModifier())
        typeParameters = GenericCapable.extractFromTypeParameters(kt.typeParameters)
        enum = kt.isEnum()

        kt.primaryConstructor?.let {
            constructor = Constructor(this, it)
        }
        kt.secondaryConstructors.forEach {
            secondaries += SecondaryConstructor(it)
        }

        parentClass = extractSuperType(kt.superTypeListEntries)
        parentInterfaces += extractSuperTypes(kt.superTypeListEntries)
        superCallArgs += extractSuperCallArgs(kt.superTypeListEntries)
        annotations += extractAnnotations(kt.annotationEntries)

        classes += extractClasses(kt.declarations)
        objects += extractObjects(kt.declarations)
        functions += extractFunctions(kt.declarations)
        properties += extractProperties(kt.declarations)

    }
*/

    override fun extends(type: KibbleType, vararg arguments: String) {
        parentClass = type
        superCallArgs.addAll(arguments.map { KibbleArgument(it) })
    }

    override fun implements(type: KibbleType) {
        implements += type
    }

    override fun addSuperType(type: KibbleType) {
        superTypes.add(type)
    }

    override fun addSuperCallArg(argument: KibbleArgument) {
        superCallArgs.add(argument)
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

/*
    override fun addProperty(name: String, type: String?, initializer: String?, modality: Modality, overriding: Boolean,
                             visibility: Visibility, mutability: Mutability, lateInit: Boolean, constructorParam: Boolean): KibbleProperty {
        TODO("maybe remove?")
        return KibbleProperty(name, type?.let { KibbleType.from(type) }, initializer, modality, overriding, lateInit).also {
            it.visibility = visibility
            it.mutability = mutability
            it.constructorParam = constructorParam
            if (constructorParam) {
                constructor.parameters.add(it)
            }
            properties += it
        }
    }
*/

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
        parentClass?.let {
            extends += "$it${superCallArgs.joinToString(prefix = "(", postfix = ")")}"
        }
/*
        if (!parentInterfaces.isEmpty()) {
            extends += parentInterfaces.joinToString(", ")
        }
*/
        if (extends.isNotEmpty()) {
            writer.write(": ")
            writer.write(extends.joinToString(", "))
        }

        writer.writeln(" {")

        objects.filter { it.companion }
                .forEach { it.toSource(writer, level + 1) }

        secondaries.forEach { it.toSource(writer, level + 1) }
        initBlock?.let {
            writer.writeln("init {", level + 1)
            it.trimIndent().split("\n").forEach {
                writer.writeln(it, level + 2)
            }
            writer.writeln("}", level + 1)
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
        parentClass?.let { file.resolve(it) }
    }

}