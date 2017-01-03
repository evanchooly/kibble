package com.antwerkz.kibble.model

import com.antwerkz.kibble.model.Visibility.PUBLIC

class KotlinFunction(): Modifiable, Visible, Hierarchical, ParameterHolder {
    var name: String = ""
    override val parameters = mutableListOf<Parameter>()
    override var isAbstract = false
    override var isFinal = false
    override var isOpen = false
    override var isOverride = false
    override var visibility = PUBLIC
    override fun toString(): String {
        var s = "fun $name("
        if (!parameters.isEmpty()) {
             s += parameters.joinToString(", ")
        }
        return s + ")"
    }
}

