package com.antwerkz.kibble.model

import com.antwerkz.kibble.model.Modality.FINAL
import com.antwerkz.kibble.model.Visibility.PUBLIC
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtSecondaryConstructor
import org.jetbrains.kotlin.psi.getOrCreateBody
import org.jetbrains.kotlin.psi.psiUtil.modalityModifier
import org.jetbrains.kotlin.psi.psiUtil.visibilityModifier

class KotlinClass(var name: String? = null) : KotlinElement, FunctionHolder, Visible, Hierarchical {
    internal constructor(kt: KtClass): this(kt.name) {
            addModifier(kt.modalityModifier()?.text)
            addModifier(kt.visibilityModifier()?.text)
            kt.getPrimaryConstructor()?.let {
                constructor = Constructor(it)
            }
            constructor?.parameters
                    ?.filter { it.mutability != null }
                    ?.forEach {
                        this += KotlinProperty(it.name, it.type, it.defaultValue, true)
                    }
            kt.getSecondaryConstructors().forEach {
                this += SecondaryConstructor(it)
            }
            kt.getOrCreateBody().declarations
                    .filter { it !is KtSecondaryConstructor }
                    .forEach {
                        KotlinElement.evaluate(it)?.let { element ->
                            when (element) {
                                is KotlinClass -> this += element
                                is KotlinFunction -> this += element
                                is KotlinProperty -> this += element
                                else -> throw IllegalArgumentException("Unknown type being added to this: $element")
                            }
                        }
                    }
    }

    override var modality: Modality = FINAL
    override val functions = mutableListOf<KotlinFunction>()
    override var visibility = PUBLIC

    var constructor: Constructor? = null
    val secondaries = mutableListOf<SecondaryConstructor>()
    var parent: KotlinClass? = null
    val nestedClasses = mutableListOf<KotlinClass>()
    val properties = mutableListOf<KotlinProperty>()

    operator fun plusAssign(ctor: SecondaryConstructor) {
        secondaries += ctor
    }
    operator fun plusAssign(property: KotlinProperty) {
        properties += property
    }

    operator fun plusAssign(nested: KotlinClass) {
        nested.parent = this
        nestedClasses += nested
    }

    override fun toString(): String {
        return "class $name"
    }
}

