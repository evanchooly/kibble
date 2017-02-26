package com.antwerkz.kibble.model

interface PropertyHolder {
    val properties: MutableList<KibbleProperty>
    var parentClass: KibbleClass?

    operator fun plusAssign(property: KibbleProperty) {
        properties += property
    }
}