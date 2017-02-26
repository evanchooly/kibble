package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import com.antwerkz.kibble.StringSourceWriter
import com.antwerkz.kibble.model.Modality.FINAL
import com.antwerkz.kibble.model.Mutability.VAL
import com.antwerkz.kibble.model.Mutability.VAR
import com.antwerkz.kibble.model.Visibility.PUBLIC
import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace
import org.jetbrains.kotlin.psi.KtAnnotationEntry
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.psi.psiUtil.allChildren
import org.jetbrains.kotlin.psi.psiUtil.modalityModifier
import org.jetbrains.kotlin.psi.psiUtil.visibilityModifier

class KibbleProperty(val name: String, var type: KibbleType,
                     var initializer: String? = null,
                     override var modality: Modality = FINAL,
                     override var overriding: Boolean = false,
                     override var visibility: Visibility = PUBLIC,
                     override var mutability: Mutability? = VAL,
                     var lateInit: Boolean = false,
                     var parent: Packaged<*>)
    : KibbleElement, Visible, Mutable, Hierarchical<KibbleProperty>, Overridable, Annotatable, Packaged<KibbleProperty> {

    internal constructor(parent: Packaged<*>, kt: KtProperty) : this(kt.name!!, KibbleType.from(kt.typeReference),
            kt.initializer?.text, parent = parent) {
        kt.modifierList
                ?.allChildren
                ?.filter { it is PsiElement && it !is PsiWhiteSpace }
                ?.forEach {
                    when (it) {
                        is KtAnnotationEntry -> extract(it)
                        else -> addModifier(it.node.text)
                    }
                }


        addModifier(kt.visibilityModifier()?.text)
        addModifier(kt.modalityModifier()?.text)
        if (kt.isVar || lateInit) {
            mutability = VAR
        }
    }

    internal var ctorParam: Boolean = false
    override var annotations: MutableList<KibbleAnnotation> = mutableListOf()

    fun addInitializer(init: String): KibbleProperty {
        initializer = init
        return this
    }

    fun isParameterized(): Boolean = type?.parameters?.isEmpty()?.not() ?: false

    override fun getFile(): KibbleFile {
        return parent.getFile()
    }

    override fun toSource(writer: SourceWriter, indentationLevel: Int) {
        annotations.forEach {
            writer.writeIndent(indentationLevel)
            writer.writeln(it.toString())
        }
        writer.writeIndent(indentationLevel)

        writer.write(visibility.toString())
        writer.write(modality.toString())
        if (overriding) {
            writer.write("override ")
        }
        if (lateInit) {
            writer.write("lateinit ")
        }
        writer.write(mutability?.toString() ?: "")
        writer.write(name)
        type.let { writer.write(": $it") }
        initializer?.let { writer.write(" = $it") }
        writer.writeln()
    }

    override fun toString(): String {
        val writer = StringSourceWriter()
        toSource(writer)
        return writer.toString()
    }
}

