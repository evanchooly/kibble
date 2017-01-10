package com.antwerkz.kibble.model

import com.antwerkz.kibble.model.Modality.FINAL
import com.antwerkz.kibble.model.Mutability.VAL
import com.antwerkz.kibble.model.Visibility.PUBLIC
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.psi.psiUtil.modalityModifier
import org.jetbrains.kotlin.psi.psiUtil.visibilityModifier

class KotlinProperty(val name: String, val type: String?, val initializer: String? = null,
                     val ctorParam: Boolean = false)
    : KotlinElement, Visible, Mutable, Hierarchical, Overridable {
    internal constructor(kt: KtProperty) : this(kt.name!!, kt.typeReference?.text, kt.initializer?.text) {
        addModifier(kt.visibilityModifier()?.text)
        addModifier(kt.modalityModifier()?.text)
    }

    override var overridden: Boolean = false
    override var modality: Modality = FINAL
    override var visibility: Visibility = PUBLIC
    override var mutability: Mutability? = VAL

    override fun toString(): String {
        var value = ""
        if (visibility != PUBLIC) {
            value += visibility.name.toLowerCase() + " "
        }
        if (modality != FINAL) {
            value += modality.name.toLowerCase() + " "
        }
        if (isOverride()) value += "override "
        value += "$mutability $name"
        type.let {
            value += ": $it"
        }
        initializer.let {
            value += " = $it"
        }

        return value
    }
}

