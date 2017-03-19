package com.antwerkz.kibble.model

interface ParameterHolder {
    val parameters: MutableList<KibbleParameter>

    fun addParameter(name: String, type: String): KibbleParameter {
        val param = KibbleParameter(name, KibbleType.from(type))
        parameters += param
        return param
    }

    operator fun plusAssign(parameter: KibbleParameter) {
        parameters += parameter
    }
}