package com.antwerkz.kibble.model

interface FunctionHolder {
    val functions: MutableList<KibbleFunction>

    operator fun plusAssign(function: KibbleFunction) {
        functions += function
    }
}