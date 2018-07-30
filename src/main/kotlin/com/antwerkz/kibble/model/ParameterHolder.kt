package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble

/**
 * Defines a type capable of holding parameters
 *
 * @property parameters the parameters held by this type
 */
interface ParameterHolder {
    val parameters: MutableList<KibbleParameter>

    /**
     * Adds a parameter to this type
     *
     * @param name the parameter name
     * @param type the parameter type
     * @param initializer the parameter initializer
     */
    fun addParameter(name: String, type: String?, initializer: String? = null, varargs: Boolean = false): KibbleParameter {
        return KibbleParameter(name, type?.let { KibbleType.from(type) }, initializer, varargs).also {
            parameters += it
        }
    }
}