package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import com.antwerkz.kibble.model.Modality.FINAL
import com.antwerkz.kibble.model.Visibility.PUBLIC
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.KtObjectDeclaration
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.psi.KtSecondaryConstructor
import org.jetbrains.kotlin.psi.KtSuperTypeCallEntry
import org.jetbrains.kotlin.psi.KtSuperTypeEntry
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry
import org.jetbrains.kotlin.psi.psiUtil.modalityModifier
import org.jetbrains.kotlin.psi.psiUtil.visibilityModifier
import org.jetbrains.kotlin.resolve.calls.callUtil.getValueArgumentsInParentheses
import org.slf4j.LoggerFactory

class KibbleClass internal constructor(var kibbleFile: KibbleFile,
                                       var name: String = "",
                                       override var modality: Modality = FINAL,
                                       override var visibility: Visibility = PUBLIC) : KibbleElement, FunctionHolder,
        Visible, Modal<KibbleClass>, Annotatable, PropertyHolder, Packaged, Extendable {

    override var pkgName: String?
        get() = kibbleFile.pkgName
        set(value) {
            kibbleFile.pkgName = value
        }

    var enclosingType: KibbleClass? = null
    override var annotations = mutableListOf<KibbleAnnotation>()
    override val functions = mutableListOf<KibbleFunction>()
    override val properties = mutableListOf<KibbleProperty>()
    val objects = mutableListOf<KibbleObject>()

    var constructor= Constructor(this)
        private set
    val secondaries: MutableList<SecondaryConstructor> = mutableListOf()
    val nestedClasses: MutableList<KibbleClass> = mutableListOf()

    companion object {
        val LOG = LoggerFactory.getLogger(KibbleClass::class.java)
    }

    override var interfaces = listOf<KibbleType>()
    override var superType: KibbleType? = null
    override var superCallArgs = listOf<String>()

    internal constructor(file: KibbleFile, kt: KtClass) : this(file, kt.name ?: "") {
        Extendable.extractSuperInformation(this, kt.getSuperTypeListEntries())

        modality = Modal.apply(kt.modalityModifier())
        visibility = Visible.apply(kt.visibilityModifier())

        kt.getPrimaryConstructor()?.let {
            constructor = Constructor(this, it)
        }
/*
        constructor?.parameters
                ?.filter { it.mutability != null }
                ?.forEach {
                    properties += KibbleProperty(kibbleFile, this, it.name, it.type, it.defaultValue, lateInit = false, ctorParam = true)
                }
*/
        kt.getSecondaryConstructors().forEach {
            secondaries += SecondaryConstructor(this, it)
        }
        extractAnnotation(kt.annotationEntries)
        kt.getBody()?.declarations
                ?.filter { it !is KtSecondaryConstructor }
                ?.forEach {
                    when (it) {
                        is KtClass -> nestedClasses += KibbleClass(file, it)
                        is KtFunction -> functions += KibbleFunction(file, it)
                        is KtProperty -> properties += KibbleProperty(this, it)
                        is KtObjectDeclaration -> objects += KibbleObject(this, it)
                        else -> throw IllegalArgumentException("Unknown type being added to this: ${it.javaClass}")
                    }
                }
    }

    fun addSecondaryConstructor(): SecondaryConstructor {
        val ctor = SecondaryConstructor(this)
        secondaries += ctor
        return ctor
    }

    override fun addProperty(name: String, type: String, initializer: String?, modality: Modality, overriding: Boolean,
                             visibility: Visibility, mutability: Mutability, lateInit: Boolean, constructorParameter: Boolean)
            : KibbleProperty {
        val property = KibbleProperty(this, name, KibbleType.from(type), initializer, modality, overriding, lateInit)
        property.visibility = visibility
        property.mutability = mutability
        if(constructorParameter) {
            constructor.parameters += property
        } else {
            properties += property
        }
        return property
    }

    override fun addFunction(name: String?, type: String, body: String): KibbleFunction {
        val function = KibbleFunction(this.kibbleFile, this, name = name, type = type, body = body)
        functions += function
        return function
    }

    fun addClass(name: String): KibbleClass {
        val klass = KibbleClass(kibbleFile, name)
        klass.enclosingType = this
        nestedClasses += klass

        return klass
    }

    operator fun plusAssign(nested: KibbleClass) {
        nested.enclosingType = this
        nestedClasses += nested
    }

    override fun toString(): String {
        return "class $name"
    }

    override fun toSource(writer: SourceWriter, level: Int) {
        writer.writeln()
        annotations.forEach { writer.writeln(it.toString(), level) }
        writer.write("$visibility${modality}class ")
        writer.write(name)

        constructor.toSource(writer, level)
        superType?.let {
            writer.write(": $it")
            writer.write(superCallArgs.joinToString(prefix = "(", postfix = ")"))
        }
        if (!interfaces.isEmpty()) {
            writer.write(interfaces.joinToString(prefix = ", "))
        }
        if (!properties.isEmpty() || !functions.isEmpty()) {
            writer.writeln(" {")
            properties.forEach { it.toSource(writer, level + 1) }
            functions.forEach { it.toSource(writer, level + 1) }
            nestedClasses.forEach { it.toSource(writer, level + 1) }

            writer.writeln("}", level)
        }
    }
}