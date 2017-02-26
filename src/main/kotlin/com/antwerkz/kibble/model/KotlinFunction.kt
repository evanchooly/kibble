package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import com.antwerkz.kibble.StringSourceWriter
import com.antwerkz.kibble.model.Modality.FINAL
import com.antwerkz.kibble.model.Visibility.PUBLIC
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.psiUtil.allChildren
import org.jetbrains.kotlin.psi.psiUtil.modalityModifier
import org.jetbrains.kotlin.psi.psiUtil.visibilityModifier

class KotlinFunction(val parent: Packaged<*>,
                     var name: String? = null,
                     override val parameters: MutableList<Parameter> = mutableListOf<Parameter>(),
                     override var visibility: Visibility = PUBLIC,
                     override var modality: Modality = FINAL,
                     var type: String = "",
                     var body: String = "",
                     override var overriding: Boolean = false)
    : Visible, Hierarchical<KotlinFunction>, ParameterHolder, KotlinElement, Overridable, Packaged<KotlinFile> {

    internal constructor(file: KotlinFile, kt: KtFunction) : this(file, kt.name) {
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

    override fun getFile(): KotlinFile {
        return parent.getFile()
    }

    override fun toString() = StringSourceWriter().apply { toSource(this) }.toString()

    override fun toSource(writer: SourceWriter, indentationLevel: Int) {
        writer.writeln()
        writer.writeIndent(indentationLevel)
        val returnType = if (type != "") ": $type " else " "
        if (overriding) {
            writer.write("override ")
        }
        val bodyText = (if (body.trim().startsWith("{")) body else "{\n$body\n}").trim()
        writer.write("${visibility}fun $modality$name(${parameters.joinToString(", ")})$returnType")
        val split = bodyText.split("\n")
        val size = split.size
        split.forEachIndexed { i, s ->
            if (i > 0) {
                if (!s.startsWith(" ")) {
                    writer.writeIndent(indentationLevel + (if (i < size - 1) 1 else 0))
                }
            }
            writer.writeln(s)
        }
    }
}

