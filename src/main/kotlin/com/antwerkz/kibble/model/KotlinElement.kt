package com.antwerkz.kibble.model

import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtDeclaration
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.KtProperty
import org.slf4j.Logger
import org.slf4j.LoggerFactory

interface KotlinElement {
    companion object {
        val LOG: Logger = LoggerFactory.getLogger(KotlinElement::class.java)
        fun evaluate(declaration: KtDeclaration): KotlinElement? {
            return when (declaration) {
                is KtClass -> KotlinClass.evaluate(declaration)
                is KtFunction -> KotlinFunction.evaluate(declaration)
                is KtProperty -> KotlinProperty(declaration)
                else -> {
                    LOG.warn("Unknown type: ${declaration.javaClass}")
                    null
                }
            }
        }
    }

    fun addModifier(modifier: String?) {
        when (modifier) {
            null -> {}
            "public", "protected", "private", "internal" -> (this as Visible).visibility = Visibility.valueOf(modifier.toUpperCase())
            "final", "abstract", "open" -> (this as Hierarchical).modality = Modality.valueOf(modifier.toUpperCase())
            "var", "val" -> (this as Mutable).mutability = Mutability.valueOf(modifier.toUpperCase())
/*
            "override" -> {
                this as Hierarchical
//                isOverride = true
            }
*/
            else -> throw IllegalStateException("Unknown modifier: $modifier")
        }
    }
}