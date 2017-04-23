package com.antwerkz.kibble.model

import com.antwerkz.kibble.model.Modality.FINAL
import com.antwerkz.kibble.model.Mutability.VAL
import com.antwerkz.kibble.model.Visibility.PUBLIC
import org.jetbrains.kotlin.psi.KtDeclaration
import org.jetbrains.kotlin.psi.KtProperty

interface PropertyHolder {
    val properties: MutableList<KibbleProperty>

    fun addProperty(name: String, type: String? = null,
                    initializer: String? = null,
                    modality: Modality = FINAL,
                    overriding: Boolean = false,
                    visibility: Visibility = PUBLIC,
                    mutability: Mutability = VAL,
                    lateInit: Boolean = false,
                    constructorParam: Boolean = false): KibbleProperty
}

internal fun PropertyHolder.extractProperties(file: KibbleFile, declarations: List<KtDeclaration>, parent: KibbleClass? = null) {
    properties += declarations
            .filterIsInstance<KtProperty>()
            .map {
                KibbleProperty(file, it)
            }
}
