package com.antwerkz.kibble.model

import com.antwerkz.kibble.model.Mutability.VAL
import com.antwerkz.kibble.model.Visibility.PUBLIC

data class Parameter(override var mutability: Mutability = VAL,
                     val name: String,
                     val type: String,
                     override var visibility: Visibility = PUBLIC) : Modifiable, Visible, Mutable