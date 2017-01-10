package com.antwerkz.kibble.model

interface Overridable {
    var overridden: Boolean

    fun isOverride(): Boolean {
        return overridden
    }
}