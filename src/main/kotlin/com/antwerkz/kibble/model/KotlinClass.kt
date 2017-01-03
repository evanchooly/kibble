package com.antwerkz.kibble.model

import com.antwerkz.kibble.model.Visibility.PUBLIC

class KotlinClass() : FunctionHolder, Modifiable, Visible {
    var name: String = ""
    override var visibility = PUBLIC
    lateinit var constructor : Constructor
    val secondaries = mutableListOf<Constructor>()
    override val functions = mutableListOf<KotlinFunction>()

    override fun toString(): String {
        return "class $name"
    }


}

