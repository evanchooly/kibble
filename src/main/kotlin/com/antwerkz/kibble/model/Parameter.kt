package com.antwerkz.kibble.model

data class Parameter(val name: String,
                     val type: String) : Modifiable {
    override fun toString(): String {
        return "$name: $type"
    }
}