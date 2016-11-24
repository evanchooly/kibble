package com.antwerkz.kotlin.model

import com.antwerkz.kotlin.model.Visibility.PUBLIC

class KotlinFunction(val name: String): Modifiable, Visible, Overridable {
    val parameters = mutableListOf<Parameter>()
    override var isOverride = false
    override var visibility = PUBLIC
}

interface Overridable {
    var isOverride: Boolean
}
