package com.antwerkz.kotlin.model

import com.antwerkz.kotlin.model.Visibility.PUBLIC

class KotlinFunction(): Modifiable, Visible, Overridable {
    var name: String? = null
    val parameters = mutableListOf<Parameter>()
    override var isOverride = false
    override var visibility = PUBLIC
}

interface Overridable {
    var isOverride: Boolean
}
