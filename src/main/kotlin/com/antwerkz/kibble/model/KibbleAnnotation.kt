package com.antwerkz.kibble.model

import org.jetbrains.kotlin.psi.KtAnnotatedExpression
import org.jetbrains.kotlin.psi.KtAnnotationEntry
import org.jetbrains.kotlin.psi.KtUserType
import org.jetbrains.kotlin.psi.KtValueArgumentList
import org.jetbrains.kotlin.psi.psiUtil.allChildren

class KibbleAnnotation internal constructor(name: String, val arguments: Map<String, Any> = mapOf()): KibbleType(name) {

    companion object {
        fun from(file: KibbleFile, annotation: KtAnnotationEntry): KibbleAnnotation {
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


    override fun toString(): String {
        var string = name
        if (arguments.isNotEmpty()) {
            string += arguments.entries.joinToString(prefix = "(", postfix = ")", transform = {
                if (it.key == "value") it.value.toString() else "${it.key} = ${it.value}"
            })
        }
        return "@$string"
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