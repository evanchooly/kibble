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
                                          var type: KibbleType? = null,
                                          var body: String = "",
                                          var bodyBlock: Boolean = true,
                                          override var overriding: Boolean = false)
    : AnnotationHolder, Visible, Modal<KibbleFunction>, ParameterHolder, KibbleElement, Overridable, GenericCapable {

    override val parameters = mutableListOf<KibbleParameter>()
    override val annotations = mutableListOf<KibbleAnnotation>()
    override val typeParameters = mutableListOf<TypeParameter>()

/*
    internal constructor(kt: KtFunction) : this(kt.name) {
        kt.valueParameters.forEach {
            parameters += KibbleParameter(it)
        }
        typeParameters += GenericCapable.extractFromTypeParameters(kt.typeParameters)
        body = kt.bodyExpression?.text?.trim() ?: ""
        bodyBlock = kt.hasBlockBody()
        if (bodyBlock) {
            body = (kt.bodyExpression?.text ?: "")
                    .drop(1)
                    .dropLast(1)
                    .trimIndent()
        }
        kt.typeReference?.let {
            type = KibbleType.from(it.text)
        }
        kt.modifierList
        modality = Modal.apply(kt.modalityModifier())
        visibility = Visible.apply(kt.visibilityModifier())
        overriding = Overridable.apply(kt)
        annotations += KibbleExtractor.extractAnnotations(kt.annotationEntries)
    }
*/


    /**
     * @return the string/source form of this type
     */
    override fun toString() = toSource().toString()

    override fun collectImports(file: KibbleFile) {
        type?.let { file.resolve(it) }
        parameters.forEach { it.collectImports(file) }
        typeParameters.forEach { it.collectImports(file) }
        annotations.forEach { it.collectImports(file) }
    }

    /**
     * @return the string/source form of this type
     */
    override fun toSource(writer: SourceWriter, level: Int): SourceWriter {
        annotations.forEach {
            it.toSource(writer, level)
            writer.writeln()
        }
        writer.write("", level)
        val returnType = if (type != null && type?.toString() != "Unit") ": $type" else ""
        if (overriding) {
            writer.write("override ")
        }
        val paramList = parameters.joinToString(", ")
        val types = if(typeParameters.isNotEmpty()) typeParameters.joinToString(prefix = "<", postfix = "> ") else ""
        writer.write("${visibility}fun $types$modality$name($paramList)$returnType")
        if (bodyBlock) {
            if (body.isNotBlank()) {
                writer.writeln(" {")
                body.trimIndent()
                        .split("\n")
                        .forEach { s ->
                            writer.writeln(s, level + 1)
                        }
                writer.write("}", level)
            }
            writer.writeln()
        } else {
            writer.writeln(" = $body")
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
        result = 31 * result + (type?.hashCode() ?: 0)
        result = 31 * result + body.hashCode()
        result = 31 * result + overriding.hashCode()
        result = 31 * result + parameters.hashCode()
        return result
    }
}

