package com.antwerkz.kibble.model

import com.antwerkz.kibble.model.Mutability.NEITHER
import com.intellij.psi.PsiElement

/**
 * Specifies the mutability of the decorated type
 */
interface Mutable {
    companion object {
        internal fun apply(modifier: PsiElement?): Mutability {
            return modifier?.text?.let {
                Mutability.valueOf(it.toUpperCase())
            } ?: NEITHER
        }
    }

    /**
     * Specifies the mutability of the decorated type
     */
    var mutability: Mutability
}