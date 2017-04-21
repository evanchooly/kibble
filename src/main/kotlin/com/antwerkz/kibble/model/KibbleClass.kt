package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import com.antwerkz.kibble.model.Modality.FINAL
import com.antwerkz.kibble.model.Visibility.PUBLIC
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.psiUtil.modalityModifier
import org.jetbrains.kotlin.psi.psiUtil.visibilityModifier

class KibbleClass internal constructor(override var file: KibbleFile,
                                       var name: String = "",
                                       override var modality: Modality = FINAL,
                                       override var visibility: Visibility = PUBLIC) : KibbleElement, FunctionHolder,
        Visible, Modal<KibbleClass>, Annotatable, PropertyHolder, Packaged, Extendable, ClassOrObjectHolder {

    override var pkgName: String?
        get() = file.pkgName
        set(value) {
            file.pkgName = value
        }

    override var superTypes = listOf<KibbleType>()
    override var superType: KibbleType? = null
    override var superCallArgs = listOf<String>()

    override var annotations = mutableListOf<KibbleAnnotation>()
    override val classes: MutableList<KibbleClass> = mutableListOf()
    override val objects: MutableList<KibbleObject> = mutableListOf()
    override val functions = mutableListOf<KibbleFunction>()
    override val properties = mutableListOf<KibbleProperty>()

    var constructor = Constructor(file, this)
        private set
    val secondaries: MutableList<SecondaryConstructor> = mutableListOf()

    internal constructor(file: KibbleFile, kt: KtClass) : this(file, kt.name ?: "") {
        Extendable.extractSuperInformation(this, kt)

        modality = Modal.apply(kt.modalityModifier())
        visibility = Visible.apply(kt.visibilityModifier())

        kt.primaryConstructor?.let {
            constructor = Constructor(this, it)
        }
        kt.secondaryConstructors.forEach {
            secondaries += SecondaryConstructor(this, it)
        }
        extractAnnotations(file, kt.annotationEntries)
        kt.getBody()?.let {
            extractClassesObjects(file, it.declarations)
            extractFunctions(file, it.declarations)
            extractProperties(file, it.declarations, this)
        }
    }

    fun addSecondaryConstructor(): SecondaryConstructor {
        return SecondaryConstructor(this).also {
            secondaries += it
        }
    }

    override fun addClass(name: String): KibbleClass {
        return KibbleClass(file, name).also {
            classes += it
        }
    }

    override fun addObject(name: String, isCompanion: Boolean): KibbleObject {
        return KibbleObject(file, this, name, isCompanion).also {
            objects += it
        }
    }

    override fun addFunction(name: String?, type: String, body: String): KibbleFunction {
        return KibbleFunction(this.file, this, name = name, type = type, body = body).also {
            functions += it
        }
    }

    override fun addProperty(name: String, type: String?, initializer: String?, modality: Modality, overriding: Boolean,
                             visibility: Visibility, mutability: Mutability, lateInit: Boolean, constructorParam: Boolean): KibbleProperty {
        return KibbleProperty(file, this, name, type?.let { KibbleType.from(type) }, initializer, modality, overriding, lateInit).also {
            it.visibility = visibility
            it.mutability = mutability
            it.constructorParam = constructorParam
            if (constructorParam) {
                constructor.parameters += it
            }
            properties += it
        }
    }

    override fun toString(): String {
        return "class $name"
    }

    override fun toSource(writer: SourceWriter, level: Int): SourceWriter {
        annotations.forEach { writer.writeln(it.toString(), level) }
        writer.write("$visibility${modality}class ", level)
        writer.write(name)

        constructor.toSource(writer, level)
        superType?.let {
            writer.write(": $it")
            writer.write(superCallArgs.joinToString(prefix = "(", postfix = ")"))
        }
        if (!superTypes.isEmpty()) {
            writer.write(superTypes.joinToString(prefix = ", "))
        }
        val nonParamProps = properties.filter { !it.constructorParam }
        if (!nonParamProps.isEmpty() || !functions.isEmpty() || !classes.isEmpty()) {
            writer.writeln(" {")
            nonParamProps.forEach { it.toSource(writer, level + 1) }

            functions.forEach { it.toSource(writer, level + 1) }
            classes.forEach { it.toSource(writer, level + 1) }

            writer.write("}", level)
        }
        writer.writeln()
        return writer
    }
}
