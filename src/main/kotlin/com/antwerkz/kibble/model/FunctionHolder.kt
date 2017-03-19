package com.antwerkz.kibble.model

interface FunctionHolder {
    val functions: MutableList<KibbleFunction>

    fun addFunction(name: String? = null, type: String = "Unit", body: String = ""): KibbleFunction

    operator fun plusAssign(function: KibbleFunction) {
        functions += function
    }
}