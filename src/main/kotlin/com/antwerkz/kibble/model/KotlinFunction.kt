package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import com.antwerkz.kibble.model.Modality.FINAL
import com.antwerkz.kibble.model.Visibility.PUBLIC
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.psiUtil.allChildren
import org.jetbrains.kotlin.psi.psiUtil.modalityModifier
import org.jetbrains.kotlin.psi.psiUtil.visibilityModifier

class KotlinFunction(var name: String? = null,
                     override val parameters: MutableList<Parameter> = mutableListOf<Parameter>(),
                     override var visibility: Visibility = PUBLIC,
                     override var modality: Modality = FINAL,
                     var type: String = "",
                     var body: String = "",
                     override var overriding: Boolean = false)
    : Visible, Hierarchical, ParameterHolder, KotlinElement, Overridable {

    internal constructor(kt: KtFunction) : this(kt.name) {
        kt.valueParameters.forEach {
            this += Parameter(it)
        }
        kt.modifierList?.allChildren?.forEach {
            addModifier(it.text)
        }
        this.body = kt.bodyExpression?.text ?: ""
        this.type = kt.typeReference?.text ?: ""
        this.addModifier(kt.visibilityModifier()?.text)
        this.addModifier(kt.modalityModifier()?.text)
    }

    override fun toString(): String {
        var s = "fun $name("
        if (!parameters.isEmpty()) {
            s += parameters.joinToString(", ")
        }
        return s + ")"
    }

    override fun toSource(writer: SourceWriter, indentationLevel: Int) {
        writer.writeln()
        writer.writeIndent(indentationLevel)
        val returnType = if (type != "") ": $type " else " "
        if (overriding) {
            writer.write("override ")
        }
        writer.write("${visibility}fun $modality$name(${parameters.joinToString(", ")})${returnType}")
        writer.writeln(body)
    }
}

