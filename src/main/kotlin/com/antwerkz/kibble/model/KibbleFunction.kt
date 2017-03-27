package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import com.antwerkz.kibble.StringSourceWriter
import com.antwerkz.kibble.model.Modality.FINAL
import com.antwerkz.kibble.model.Visibility.PUBLIC
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.psiUtil.allChildren
import org.jetbrains.kotlin.psi.psiUtil.modalityModifier
import org.jetbrains.kotlin.psi.psiUtil.visibilityModifier

class KibbleFunction internal constructor(val file: KibbleFile,
                                          val parent: KibbleClass? = null,
                                          var name: String? = null,
                                          override var visibility: Visibility = PUBLIC,
                                          override var modality: Modality = FINAL,
                                          var type: String = "Unit",
                                          var body: String = "",
                                          override var overriding: Boolean = false)
    : Visible, Modal<KibbleFunction>, ParameterHolder, KibbleElement, Overridable, Packaged {

    override val parameters = mutableListOf<KibbleParameter>()

    override var pkgName: String?
        get() = file.pkgName
        set(value) {
            file.pkgName = value
        }

    internal constructor(file: KibbleFile, kt: KtFunction) : this(file, null, kt.name) {
        parse(kt)
    }

    internal constructor(parent: KibbleClass, kt: KtFunction) : this(parent.kibbleFile, parent, kt.name) {
        parse(kt)
    }

    private fun parse(kt: KtFunction) {
        kt.valueParameters.forEach {
            this += KibbleParameter(it)
        }
        kt.modifierList?.allChildren?.forEach {
            addModifier(it.text)
        }
        this.body = kt.bodyExpression?.text ?: ""
        this.type = kt.typeReference?.text ?: ""
        this.addModifier(kt.visibilityModifier()?.text)
        this.addModifier(kt.modalityModifier()?.text)
    }

    override fun toString() = StringSourceWriter().apply { toSource(this) }.toString()

    override fun toSource(writer: SourceWriter, indentationLevel: Int) {
        writer.writeln()
        writer.writeIndent(indentationLevel)
        val returnType = if (type != "" && type != "Unit") ": $type " else " "
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

