package com.antwerkz.kibble.model

import com.antwerkz.kibble.model.Modality.FINAL
import com.antwerkz.kibble.model.Mutability.VAL
import com.antwerkz.kibble.model.Visibility.PUBLIC

interface PropertyHolder {
    val properties: MutableList<KibbleProperty>

    fun addProperty(name: String, type: String,
                    initializer: String? = null,
                    modality: Modality = FINAL,
                    overriding: Boolean = false,
                    visibility: Visibility = PUBLIC,
                    mutability: Mutability = VAL,
                    lateInit: Boolean = false,
                    constructorParam: Boolean = false
    ): KibbleProperty
}