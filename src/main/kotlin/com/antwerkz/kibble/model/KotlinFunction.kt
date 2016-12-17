package com.antwerkz.kibble.model

import com.antwerkz.kibble.model.Visibility.PUBLIC

class KotlinFunction(): Modifiable, Visible, Overridable {
    var name: String? = null
    val parameters = mutableListOf<Parameter>()
    override var isOverride = false
    override var visibility = PUBLIC
}

interface Overridable {
    var isOverride: Boolean
}
