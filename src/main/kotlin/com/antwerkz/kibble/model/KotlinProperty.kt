package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import com.antwerkz.kibble.model.Modality.FINAL
import com.antwerkz.kibble.model.Mutability.VAL
import com.antwerkz.kibble.model.Mutability.VAR
import com.antwerkz.kibble.model.Visibility.PUBLIC
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.psi.psiUtil.allChildren
import org.jetbrains.kotlin.psi.psiUtil.modalityModifier
import org.jetbrains.kotlin.psi.psiUtil.visibilityModifier

class KotlinProperty(val name: String, val type: String?,
                     val initializer: String? = null,
                     override var modality: Modality = FINAL,
                     override var overriding: Boolean = false,
                     override var visibility: Visibility = PUBLIC,
                     override var mutability: Mutability? = VAL,
                     var lateInit: Boolean = false)
    : KotlinElement, Visible, Mutable, Hierarchical, Overridable {

    internal constructor(kt: KtProperty) : this(kt.name!!, kt.typeReference?.text, kt.initializer?.text) {
        println("this = ${this}")
        kt.modifierList
                ?.allChildren
                ?.forEach {
                    println("it.node.text = ${it.node.text}")
                    addModifier(it.node.text)
                }
        kt.children
                .forEach {
                    println("child it.node.text = ${it.node.text}")
                }
        addModifier(kt.visibilityModifier()?.text)
        addModifier(kt.modalityModifier()?.text)
        if (kt.isVar || lateInit) {
            mutability = VAR
        }
    }

    internal var ctorParam: Boolean = false

    override fun toSource(writer: SourceWriter, indentationLevel: Int) {
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
        type?.let { writer.write(": $it") }
        initializer?.let { writer.write(" = $it") }
        writer.writeln()
    }

    override fun toString(): String {
        var value = ""
        if (visibility != PUBLIC) {
            value += visibility.name.toLowerCase() + " "
        }
        if (modality != FINAL) {
            value += modality.name.toLowerCase() + " "
        }
        if (isOverride()) value += "override "
        value += "$mutability $name"
        type.let {
            value += ": $it"
        }
        initializer.let {
            value += " = $it"
        }

        return value
    }
}

