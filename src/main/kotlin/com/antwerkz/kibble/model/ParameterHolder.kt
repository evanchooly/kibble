package com.antwerkz.kibble.model

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
    fun addParameter(name: String, type: String?, initializer: String? = null): KibbleParameter {
        val param = KibbleParameter(name, type?.let { KibbleType.from(type) }, initializer)
        parameters += param
        return param
    }
}