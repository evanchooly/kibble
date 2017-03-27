package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import com.antwerkz.kibble.model.Modality.FINAL
import com.antwerkz.kibble.model.Visibility.PUBLIC
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.psi.KtSecondaryConstructor
import org.jetbrains.kotlin.psi.KtSuperTypeCallEntry
import org.jetbrains.kotlin.psi.KtSuperTypeEntry
import org.jetbrains.kotlin.psi.psiUtil.modalityModifier
import org.jetbrains.kotlin.psi.psiUtil.visibilityModifier
import org.jetbrains.kotlin.resolve.calls.callUtil.getValueArgumentsInParentheses
import org.slf4j.LoggerFactory

class KibbleClass internal constructor(var kibbleFile: KibbleFile,
                                       var name: String = "",
                                       override var modality: Modality = FINAL,
                                       override var visibility: Visibility = PUBLIC) : KibbleElement, FunctionHolder,
        Visible, Modal<KibbleClass>, Annotatable, PropertyHolder, Packaged {

    override var pkgName: String?
        get() = kibbleFile.pkgName
        set(value) {
            kibbleFile.pkgName = value
        }

    var enclosingType: KibbleClass? = null
    override var annotations = mutableListOf<KibbleAnnotation>()
    override val functions = mutableListOf<KibbleFunction>()
    override val properties = mutableListOf<KibbleProperty>()

    var constructor= Constructor(this)
        private set
    val secondaries: MutableList<SecondaryConstructor> = mutableListOf()
    val nestedClasses: MutableList<KibbleClass> = mutableListOf()

    companion object {
        val LOG = LoggerFactory.getLogger(KibbleClass::class.java)
    }

    var superType: KibbleType? = null
    var interfaces = listOf<KibbleType>()
    var superCallArgs = listOf<String>()

    internal constructor(file: KibbleFile, kt: KtClass) : this(file, kt.name ?: "") {
        val superTypeListEntries = kt.getSuperTypeListEntries()
        val list = mutableListOf<KibbleType>()
        val superCallArgs = mutableListOf<String>()
        superTypeListEntries
                .forEach {
                    when (it) {
                        is KtSuperTypeCallEntry -> {
                            superType = KibbleType.from(it.typeReference)
                            it.getValueArgumentsInParentheses().forEach { arg ->
                                superCallArgs += arg.getArgumentExpression()!!.text
                            }
                        }
                        is KtSuperTypeEntry -> {
                            KibbleType.from(it.typeReference).let {
                                list += it
                            }
                        }
                        else -> {
                            LOG.warn("Unknown super type entry: ${it.javaClass.name}")
                        }
                    }
                }
        this.interfaces = list
        this.superCallArgs = superCallArgs

        addModifier(kt.modalityModifier()?.text)
        addModifier(kt.visibilityModifier()?.text)
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
        extract(kt.annotationEntries)
        kt.getBody()?.declarations
                ?.filter { it !is KtSecondaryConstructor }
                ?.forEach {
                    when (it) {
                        is KtClass -> nestedClasses += KibbleClass(file, it)
                        is KtFunction -> functions += KibbleFunction(file, it)
                        is KtProperty -> properties += KibbleProperty(this, it)
                        else -> throw IllegalArgumentException("Unknown type being added to this: $it")
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

    override fun toSource(writer: SourceWriter, indentationLevel: Int) {
        writer.writeln()
        writer.writeIndent(indentationLevel)
        annotations.forEach { writer.writeln(it.toString()) }
        writer.write("$visibility${modality}class ")
        writer.write(name)

        constructor.toSource(writer, indentationLevel)
        superType?.let {
            writer.write(": $it")
            writer.write(superCallArgs.joinToString(prefix = "(", postfix = ")"))
        }
        if (!interfaces.isEmpty()) {
            writer.write(interfaces.joinToString(prefix = ", "))
        }
        if (!properties.isEmpty() || !functions.isEmpty()) {
            writer.writeln(" {")
            properties.forEach { it.toSource(writer, indentationLevel + 1) }
            functions.forEach { it.toSource(writer, indentationLevel + 1) }
            nestedClasses.forEach { it.toSource(writer, indentationLevel + 1) }

            writer.writeIndent(indentationLevel)
            writer.writeln("}")
        }
    }
}