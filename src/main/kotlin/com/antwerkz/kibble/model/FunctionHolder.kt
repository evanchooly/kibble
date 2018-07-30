package com.antwerkz.kibble.model

/**
 * Represents this type can hold functions
 *
 * @property functions the list of functions held by this type
 */
interface FunctionHolder {
    val functions: MutableList<KibbleFunction>

    /**
     * Adds a new function to this type
     *
     * @param name the function name
     * @param type the function type
     * @param body the function body
     *
     * @return the new function
     */
    fun addFunction(name: String? = null, type: String = "Unit", body: String = ""): KibbleFunction

    fun getFunctions(name: String): List<KibbleFunction> {
        return functions.filter { it.name == name }
    }
}