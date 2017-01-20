package com.antwerkz.kibble.model

interface Overridable {
    var overriding: Boolean

    fun isOverride(): Boolean {
        return overriding
    }
}