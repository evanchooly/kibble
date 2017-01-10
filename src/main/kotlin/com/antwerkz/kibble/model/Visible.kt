package com.antwerkz.kibble.model

import com.antwerkz.kibble.model.Visibility.INTERNAL
import com.antwerkz.kibble.model.Visibility.PRIVATE
import com.antwerkz.kibble.model.Visibility.PROTECTED
import com.antwerkz.kibble.model.Visibility.PUBLIC

interface Visible {
    var visibility: Visibility
    fun isInternal(): Boolean {
        return visibility == INTERNAL
    }

    fun isPrivate(): Boolean {
        return visibility == PRIVATE
    }

    fun isProtected(): Boolean {
        return visibility == PROTECTED
    }

    fun isPublic(): Boolean {
        return visibility == PUBLIC
    }
}