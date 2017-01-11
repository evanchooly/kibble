package com.antwerkz.kibble.model

import com.antwerkz.kibble.model.Modality.FINAL
import com.antwerkz.kibble.model.Visibility.PUBLIC
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.psiUtil.modalityModifier
import org.jetbrains.kotlin.psi.psiUtil.visibilityModifier

class KotlinFunction(var name: String? = null): Visible, Hierarchical, ParameterHolder, KotlinElement {
    internal constructor(kt: KtFunction): this(kt.name) {
            kt.valueParameters.forEach {
                this += Parameter(it)
            }
            this.body = kt.bodyExpression?.text ?: ""
            this.type = kt.typeReference?.text ?: ""
            this.addModifier(kt.visibilityModifier()?.text)
            this.addModifier(kt.modalityModifier()?.text)
        }

    override val parameters = mutableListOf<Parameter>()
    override var visibility = PUBLIC
    override var modality: Modality = FINAL
    var type: String = ""
    var body: String = ""

    override fun toString(): String {
        var s = "fun $name("
        if (!parameters.isEmpty()) {
             s += parameters.joinToString(", ")
        }
        return s + ")"
    }
}

