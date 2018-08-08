package com.antwerkz.kibble.model

import com.antwerkz.kibble.KibbleContext

/**
 * Represents this type can hold functions
 *
 * @property functions the list of functions held by this type
 */
interface FunctionHolder {
    val context: KibbleContext
    val functions: List<KibbleFunction>

    /**
     * Adds a new function to this type
     *
     * @param name the function name
     * @param type the function type
     * @param body the function body
     *
     * @return the new function
     */
    fun addFunction(name: String? = null, type: String = "Unit", body: String? = null): KibbleFunction {
        return addFunction(KibbleFunction(name = name, type = KibbleType.from(type), body = body?.trimIndent(), context = context))
    }

    fun addFunction(function: KibbleFunction): KibbleFunction

    fun getFunctions(name: String): List<KibbleFunction> {
        return functions.filter { it.name == name }
    }
}