package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import com.antwerkz.kibble.model.Modality.FINAL
import com.antwerkz.kibble.model.Visibility.PUBLIC
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.psiUtil.modalityModifier
import org.jetbrains.kotlin.psi.psiUtil.visibilityModifier

/**
 * Defines a function
 *
 * @property name the function name
 * @property parameters the function parameters
 * @property type the function type
 * @property body the function body
 * @property overriding true if this function overrides a function in a parent type
 */
class KibbleFunction internal constructor(var name: String? = null,
                                          override var visibility: Visibility = PUBLIC,
                                          override var modality: Modality = FINAL,
                                          var type: String = "Unit",
                                          var body: String = "",
                                          var bodyBlock: Boolean = true,
                                          override var overriding: Boolean = false)
    : Visible, Modal<KibbleFunction>, ParameterHolder, KibbleElement, Overridable {

    override val parameters = mutableListOf<KibbleParameter>()

    internal constructor(kt: KtFunction) : this(kt.name) {
        parse(kt)
    }

    private fun parse(kt: KtFunction) {
        kt.valueParameters.forEach {
            parameters += KibbleParameter(it)
        }
        this.body = kt.bodyExpression?.text?.trim() ?: ""
        this.bodyBlock = kt.hasBlockBody()
        if (bodyBlock) {
            this.body = (kt.bodyExpression?.text ?: "")
                    .drop(1)
                    .dropLast(1)
                    .trimIndent()
        }
        this.type = kt.typeReference?.text ?: ""

        kt.modifierList
        modality = Modal.apply(kt.modalityModifier())
        visibility = Visible.apply(kt.visibilityModifier())
        overriding = Overridable.apply(kt)
    }


    /**
     * @return the string/source form of this type
     */
    override fun toString() = toSource().toString()

    /**
     * @return the string/source form of this type
     */
    override fun toSource(writer: SourceWriter, level: Int): SourceWriter {
        writer.write("", level)
        val returnType = if (type != "" && type != "Unit") ": $type" else ""
        if (overriding) {
            writer.write("override ")
        }
        val paramList = parameters.joinToString(", ")
        writer.write("${visibility}fun $modality$name($paramList)$returnType")
        if (bodyBlock) {
            writer.writeln(" {")
            body.trimIndent()
                    .split("\n").forEach { s ->
                writer.writeln(s, level + 1)
            }
            writer.writeln("}", level)
        } else {
            writer.write(" = $body")
        }
        return writer
    }

    /**
     * @return true if `other` is equal to this
     */
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

    /**
     * @return the hash code
     */
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

