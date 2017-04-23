package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import org.jetbrains.kotlin.psi.KtAnnotatedExpression
import org.jetbrains.kotlin.psi.KtAnnotationEntry
import org.jetbrains.kotlin.psi.KtUserType
import org.jetbrains.kotlin.psi.KtValueArgumentList
import org.jetbrains.kotlin.psi.psiUtil.allChildren

/**
 * Represents an annotation in kotlin source code.
 *
 * @property arguments the values passed to the annotation
 */
class KibbleAnnotation internal constructor(name: String, val arguments: Map<String, Any> = mapOf()) : KibbleType(name), KibbleElement {

    companion object {
        internal fun from(file: KibbleFile, annotation: KtAnnotationEntry): KibbleAnnotation {
            val arguments = annotation.allChildren
                    .filterIsInstance(KtValueArgumentList::class.java)
                    .firstOrNull()
                    ?.arguments
                    ?.map {
                        val name = it.getArgumentName()?.text ?: "value"
                        val expression = it.getArgumentExpression()
                        name to when (expression) {
                            is KtAnnotatedExpression -> {
                                from(file, expression.allChildren.filterIsInstance(KtAnnotationEntry::class.java).first())
                            }
                            else -> expression?.text ?: ""
                        }
                    }?.associateBy({ it.first }, { it.second })
                    ?: mapOf()
            return KibbleAnnotation(annotation.typeReference?.typeElement?.name
                    ?: (annotation.typeReference?.typeElement as KtUserType).referencedName ?: "",
                    arguments)
        }
    }


    /**
     * @return the source form of this annotation
     */
    override fun toString() = toSource().toString()

    override fun toSource(writer: SourceWriter, level: Int): SourceWriter {
        var string = "@$name"
        if (arguments.isNotEmpty()) {
            string += arguments.entries.joinToString(prefix = "(", postfix = ")",
                    transform = {
                        if (it.key == "value") it.value.toString() else "${it.key} = ${it.value}"
                    })
        }
        writer.write(string)
        return writer
    }

    /**
     * @return the value of the named parameter for the annotation
     */
    operator fun get(name: String): String? {
        return arguments[name] as String?
    }

    /**
     * @return the value of the "value" parameter for the annotation
     */
    fun getAnnotationValue(name: String): KibbleAnnotation? {
        return arguments[name] as KibbleAnnotation?
    }

    /**
     * @return the value of the "value" parameter for the annotation
     */
    fun getValue(): String? {
        return arguments["value"] as String
    }
}