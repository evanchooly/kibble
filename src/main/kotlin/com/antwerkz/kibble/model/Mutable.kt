package com.antwerkz.kibble.model

import com.antwerkz.kibble.model.Mutability.NEITHER
import org.jetbrains.kotlin.com.intellij.psi.PsiElement

interface Mutable {
    companion object {
        internal fun apply(modifier: PsiElement?): Mutability {
            return modifier?.text?.let {
                Mutability.valueOf(it.toUpperCase())
            } ?: NEITHER
        }
    }

    var mutability: Mutability
}