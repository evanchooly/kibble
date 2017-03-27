package com.antwerkz.kibble.model

import org.jetbrains.kotlin.psi.KtAnnotationEntry
import org.jetbrains.kotlin.psi.KtUserType
import org.jetbrains.kotlin.psi.KtValueArgumentList
import org.jetbrains.kotlin.psi.KtValueArgumentName
import org.jetbrains.kotlin.psi.psiUtil.allChildren

class KibbleAnnotation internal constructor(name: String, val arguments: Map<String, String?> = mapOf()) : KibbleType(name) {
    companion object {
        fun from(annotation: KtAnnotationEntry): KibbleAnnotation {
            return KibbleAnnotation(
                    annotation.typeReference?.typeElement?.name
                            ?: (annotation.typeReference?.typeElement as KtUserType).referencedName ?: "",
                    annotation.allChildren
                            .filterIsInstance(KtValueArgumentList::class.java)
                            .firstOrNull()
                            ?.arguments
                            ?.map {
                                (it.getArgumentName() ?: "value") to it.getArgumentExpression()?.text
                            }
                            ?.associateBy({
                                val key = it.first
                                when(key) {
                                    is KtValueArgumentName -> key.text
                                    else -> key.toString()
                                }
                            }, {
                                val value = it.second
                                value
                            }) ?: mapOf())
        }
    }

    
    override fun toString(): String {
        var string = name
        if(arguments.isNotEmpty()) {
            string += arguments.entries.joinToString(prefix = "(", postfix = ")", transform = {
                if (it.key == "value" && it.value != null) it.value!! else "${it.key} = ${it.value}"
            })
        }
        return "@$string"
    }

    /**
     * @return the value of the named parameter for the annotation
     */
    operator fun get(name: String): String? {
        return arguments[name]
    }

    /**
     * @return the value of the "value" parameter for the annotation
     */
    fun getValue(): String? {
        return arguments["value"]
    }
}