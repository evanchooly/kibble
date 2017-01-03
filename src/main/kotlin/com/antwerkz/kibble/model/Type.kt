package com.antwerkz.kibble.model

class Type() {
    var name: String? = null
    private val descriptors = mutableListOf<Type>()

    operator fun plusAssign(desc: Type) {
        descriptors += desc
    }

    constructor(name: String) : this() {
        this.name = name
    }
}
