package com.antwerkz.kibble.model

import com.antwerkz.kibble.model.Mutability.VAL
import com.antwerkz.kibble.model.Visibility.PUBLIC

data class Parameter(val name: String,
                     val type: String) : Modifiable