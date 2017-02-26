package com.antwerkz.kibble.model

interface PropertyHolder {
    val properties: MutableList<KotlinProperty>
    var parentClass: KotlinClass?

    operator fun plusAssign(property: KotlinProperty) {
        properties += property
    }
}