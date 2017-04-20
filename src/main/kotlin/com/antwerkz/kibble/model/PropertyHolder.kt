package com.antwerkz.kibble.model

import com.antwerkz.kibble.model.Modality.FINAL
import com.antwerkz.kibble.model.Mutability.VAL
import com.antwerkz.kibble.model.Visibility.PUBLIC
import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtProperty

interface PropertyHolder {
    companion object {
        internal fun apply(file: KibbleFile, kt: KtClassOrObject, parent: KibbleClass? = null): List<KibbleProperty> {
            return kt.getBody()?.declarations
                    ?.filterIsInstance<KtProperty>()
                    ?.map {
                        KibbleProperty(file, parent, it)
                    } ?: listOf()

        }
    }

    val properties: MutableList<KibbleProperty>

    fun addProperty(name: String, type: String? = null,
                    initializer: String? = null,
                    modality: Modality = FINAL,
                    overriding: Boolean = false,
                    visibility: Visibility = PUBLIC,
                    mutability: Mutability = VAL,
                    lateInit: Boolean = false,
                    constructorParam: Boolean = false
    ): KibbleProperty
}