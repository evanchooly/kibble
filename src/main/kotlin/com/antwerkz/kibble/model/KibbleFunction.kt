package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import com.antwerkz.kibble.model.Modality.FINAL
import com.antwerkz.kibble.model.Visibility.PUBLIC
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.psiUtil.modalityModifier
import org.jetbrains.kotlin.psi.psiUtil.visibilityModifier

class KibbleFunction internal constructor(override val file: KibbleFile,
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

    internal constructor(file: KibbleFile, kt: KtFunction) : this(file, kt.name) {
        parse(kt)
    }

    internal constructor(parent: KibbleClass, kt: KtFunction) : this(parent.file, kt.name) {
        parse(kt)
    }

    private fun parse(kt: KtFunction) {
        kt.valueParameters.forEach {
            this += KibbleParameter(file, it)
        }
        this.body = (kt.bodyExpression?.text ?: "")
                .drop(1)
                .dropLast(1)
                .trimIndent()
        this.type = kt.typeReference?.text ?: ""

        kt.modifierList
        modality = Modal.apply(kt.modalityModifier())
        visibility = Visible.apply(kt.visibilityModifier())
        overriding = Overridable.apply(kt)
    }


    override fun toString() = toSource().toString()

    override fun toSource(writer: SourceWriter, level: Int): SourceWriter {
        writer.writeln()
        writer.write("", level)
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
                    writer.write("", level + (if (i < size - 1) 1 else 0))
                }
            }
            writer.writeln(s)
        }
        return writer
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as KibbleFunction

        if (name != other.name) return false
        if (visibility != other.visibility) return false
        if (modality != other.modality) return false
        if (type != other.type) return false
        if (body != other.body) return false
        if (overriding != other.overriding) return false
        if (parameters != other.parameters) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name?.hashCode() ?: 0
        result = 31 * result + visibility.hashCode()
        result = 31 * result + modality.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + body.hashCode()
        result = 31 * result + overriding.hashCode()
        result = 31 * result + parameters.hashCode()
        return result
    }
}

