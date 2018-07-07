package com.antwerkz.kibble.model

class KibbleFunctionType internal constructor(name: String, var parameters: List<KibbleParameter>, val type: KibbleType?,
                                              val receiver: KibbleType?)
    : KibbleType(className = name) {

    override fun toString(): String {
        var string = receiver?.let {
            "$receiver."
        } ?: ""
        string += parameters.joinToString(", ", prefix = "(", postfix = ")")
        type?.let {
            if (type.toString() != "Unit") {
                string += " -> $type"
            }
        }
        return string
    }
}