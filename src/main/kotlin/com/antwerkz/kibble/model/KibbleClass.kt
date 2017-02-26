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

class KibbleClass(val parent: KibbleFile,
                  var name: String = "",
                  override var modality: Modality = FINAL,
                  override val functions: MutableList<KibbleFunction> = mutableListOf<KibbleFunction>(),
                  override var visibility: Visibility = PUBLIC,

                  var constructor: Constructor? = null,
                  val secondaries: MutableList<SecondaryConstructor> = mutableListOf<SecondaryConstructor>(),
                  var enclosingType: KibbleClass? = null,
                  val nestedClasses: MutableList<KibbleClass> = mutableListOf<KibbleClass>(),
                  override val properties: MutableList<KibbleProperty> = mutableListOf<KibbleProperty>()
) : KibbleElement, FunctionHolder, Visible, Hierarchical<KibbleClass>, Annotatable, PropertyHolder, Packaged<KibbleClass> {
    override var annotations: MutableList<KibbleAnnotation> = mutableListOf()

    companion object {
        val LOG = LoggerFactory.getLogger(KibbleClass::class.java)
    }

    var superType: KibbleType? = null
    var interfaces = listOf<KibbleType>()
    var superCallArgs = listOf<String>()
    override var parentClass: KibbleClass? = this

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
            constructor = Constructor(it)
        }
        constructor?.parameters
                ?.filter { it.mutability != null }
                ?.forEach {
                    val kotlinProperty = KibbleProperty(it.name, it.type, it.defaultValue, lateInit = false, parent = this)
                    kotlinProperty.ctorParam = true
                    this += kotlinProperty
                }
        kt.getSecondaryConstructors().forEach {
            this += SecondaryConstructor(it)
        }
        extract(kt.annotationEntries)
        kt.getBody()?.declarations
                ?.filter { it !is KtSecondaryConstructor }
                ?.forEach {
                    when (it) {
                        is KtClass -> this += KibbleClass(file, it)
                        is KtFunction -> this += KibbleFunction(file, it)
                        is KtProperty -> this += KibbleProperty(this, it)
                        else -> throw IllegalArgumentException("Unknown type being added to this: $it")
                    }
                }
    }

    override fun getFile(): KibbleFile {
        return parent
    }

    operator fun plusAssign(ctor: SecondaryConstructor) {
        secondaries += ctor
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
        constructor?.toSource(writer, indentationLevel)
        superType?.let {
            writer.write(": $it")
            writer.write(superCallArgs.joinToString(prefix = "(", postfix = ")"))
        }
        if (!interfaces.isEmpty()) {
            writer.write(interfaces.joinToString(prefix = ", "))
        }
        if (!properties
                .filter { !it.ctorParam }
                .isEmpty() || !functions.isEmpty()) {
            writer.writeln(" {")
            properties.filter { !it.ctorParam }
                    .forEach { it.toSource(writer, indentationLevel + 1) }
            functions.forEach { it.toSource(writer, indentationLevel + 1) }
            writer.writeln("}")
        }
    }
}