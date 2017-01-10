package com.antwerkz.kibble.model

import com.antwerkz.kibble.model.Modality.FINAL
import com.antwerkz.kibble.model.Visibility.PRIVATE
import com.antwerkz.kibble.model.Visibility.PUBLIC
import org.jetbrains.kotlin.psi.KtDeclaration
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.psiUtil.isPrivate
import org.jetbrains.kotlin.psi.psiUtil.modalityModifier
import org.jetbrains.kotlin.psi.psiUtil.visibilityModifier

class KotlinFunction(var name: String? = null): Visible, Hierarchical, ParameterHolder, KotlinElement {
    companion object  {
        fun evaluate(kt: KtFunction): KotlinFunction {
            val kotlinFunction = KotlinFunction(kt.name)
            kt.valueParameters.forEach {
                kotlinFunction += Parameter.evaluate(it)
            }
            kotlinFunction.body = kt.bodyExpression?.text ?: ""
            kotlinFunction.type = kt.typeReference?.text ?: ""
            kotlinFunction.addModifier(kt.visibilityModifier()?.text)
            kotlinFunction.addModifier(kt.modalityModifier()?.text)

            return kotlinFunction
        }
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

