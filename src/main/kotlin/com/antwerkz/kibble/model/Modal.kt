package com.antwerkz.kibble.model

import com.antwerkz.kibble.model.Modality.FINAL
import org.jetbrains.kotlin.com.intellij.psi.PsiElement


@Suppress("UNCHECKED_CAST")
interface Modal<out T> {
    companion object {
        internal fun apply(modifier: PsiElement?): Modality {
            return modifier?.text?.let {
                Modality.valueOf(it.toUpperCase())
            } ?: FINAL
        }
    }
    var modality: Modality

    fun isAbstract(): Boolean {
        return Modality.ABSTRACT == modality
    }

    fun markAbstract(): T {
        modality = Modality.ABSTRACT
        return this as T
    }

    fun isFinal(): Boolean {
        return Modality.FINAL == modality
    }

    fun markFinal(): T {
        modality = Modality.FINAL
        return this as T
    }

    fun isOpen(): Boolean {
        return Modality.OPEN == modality
    }

    fun markOpen(): T {
        modality = Modality.OPEN
        return this as T
    }

}
