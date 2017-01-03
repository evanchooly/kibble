package com.antwerkz.kibble.model

interface ParameterHolder {
    val parameters: MutableList<Parameter>

    operator fun plusAssign(parameter: Parameter) {
        parameters += parameter
    }
}