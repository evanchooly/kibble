package com.antwerkz.kibble.model

import com.antwerkz.kibble.model.Visibility.INTERNAL
import com.antwerkz.kibble.model.Visibility.PRIVATE
import com.antwerkz.kibble.model.Visibility.PROTECTED
import com.antwerkz.kibble.model.Visibility.PUBLIC
import com.antwerkz.kibble.model.Visibility.valueOf
import org.jetbrains.kotlin.com.intellij.psi.PsiElement

/**
 * Represents the visibility of an element
 *
 * @property visibility the visibility
 */
interface Visible {
    var visibility: Visibility

    /**
     * @return true if the element is internal
     */
    fun isInternal(): Boolean {
        return visibility == INTERNAL
    }

    /**
     * @return true if the element is private
     */
    fun isPrivate(): Boolean {
        return visibility == PRIVATE
    }

    /**
     * @return true if the element is protected
     */
    fun isProtected(): Boolean {
        return visibility == PROTECTED
    }

    /**
     * @return true if the element is public
     */
    fun isPublic(): Boolean {
        return visibility == PUBLIC
    }
}