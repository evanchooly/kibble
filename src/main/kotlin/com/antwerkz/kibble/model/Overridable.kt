package com.antwerkz.kibble.model

import org.jetbrains.kotlin.psi.KtModifierListOwner
import org.jetbrains.kotlin.psi.psiUtil.allChildren

interface Overridable {
    companion object {
        internal fun apply(kt: KtModifierListOwner): Boolean {
            return kt.modifierList?.allChildren?.find { it.text == "override" } != null
        }
    }

    var overriding: Boolean

    fun isOverride(): Boolean {
        return overriding
    }
}