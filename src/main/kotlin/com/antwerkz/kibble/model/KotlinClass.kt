package com.antwerkz.kibble.model

import com.antwerkz.kibble.model.Modality.FINAL
import com.antwerkz.kibble.model.Visibility.PUBLIC
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtSecondaryConstructor
import org.jetbrains.kotlin.psi.getOrCreateBody
import org.jetbrains.kotlin.psi.psiUtil.modalityModifier
import org.jetbrains.kotlin.psi.psiUtil.visibilityModifier

class KotlinClass(var name: String? = null) : KotlinElement, FunctionHolder, Visible, Hierarchical {
    companion object {
        fun evaluate(kt: KtClass): KotlinClass {
            val kotlinClass = KotlinClass(kt.name)
            kotlinClass.addModifier(kt.modalityModifier()?.text)
            kotlinClass.addModifier(kt.visibilityModifier()?.text)
            kotlinClass.constructor = Constructor.evaluate(kt.getPrimaryConstructor())
            kotlinClass.constructor?.parameters
                    ?.filter { it.mutability != null }
                    ?.forEach {
                        kotlinClass += KotlinProperty(it.name, it.type, it.defaultValue, true)
                    }
            kt.getSecondaryConstructors().forEach {
                kotlinClass += SecondaryConstructor.evaluate(it)
            }
            kt.getOrCreateBody().declarations
                    .filter { it !is KtSecondaryConstructor }
                    .forEach {
                        KotlinElement.evaluate(it)?.let { element ->
                            when (element) {
                                is KotlinClass -> kotlinClass += element
                                is KotlinFunction -> kotlinClass += element
                                is KotlinProperty -> kotlinClass += element
                                else -> throw IllegalArgumentException("Unknown type being added to KotlinClass: $element")
                            }
                        }
                    }
            return kotlinClass
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

