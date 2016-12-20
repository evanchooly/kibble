package com.antwerkz.kibble.model

interface FunctionHolder {
    val functions: MutableList<KotlinFunction>

    operator fun plusAssign(function: KotlinFunction) {
        functions += function
    }
}