package com.antwerkz.kibble.model

import com.antwerkz.kibble.model.Modality.FINAL
import org.jetbrains.kotlin.com.intellij.psi.PsiElement

/**
 * Represents the mode of an element
 *
 * @property modality the mode
 */
@Suppress("UNCHECKED_CAST")
interface Modal<out T> {
    var modality: Modality

    /**
     * @return true if this element is abstract
     */
    fun isAbstract(): Boolean {
        return Modality.ABSTRACT == modality
    }

    /**
     * Mark this element as abstract
     */
    fun markAbstract(): T {
        modality = Modality.ABSTRACT
        return this as T
    }

    /**
     * @return true if this element is final
     */
    fun isFinal(): Boolean {
        return Modality.FINAL == modality
    }

    /**
     * Mark this element as final
     */
    fun markFinal(): T {
        modality = Modality.FINAL
        return this as T
    }

    /**
     * @return true if this element is open
     */
    fun isOpen(): Boolean {
        return Modality.OPEN == modality
    }

    /**
     * Mark this element as open
     */
    fun markOpen(): T {
        modality = Modality.OPEN
        return this as T
    }

    /**
     * @return true if this element is sealed
     */
    fun isSealed(): Boolean {
        return Modality.SEALED == modality
    }

    /**
     * Mark this element as sealed
     */
    fun markSealed(): T {
        modality = Modality.SEALED
        return this as T
    }

}
