package com.antwerkz.kotlin.model

import com.antwerkz.kotlin.model.Mutability.VAL
import com.antwerkz.kotlin.model.Visibility.PUBLIC

data class Parameter(override var mutability: Mutability = VAL,
                     val name: String,
                     val type: String,
                     override var visibility: Visibility = PUBLIC) : Modifiable, Visible, Mutable