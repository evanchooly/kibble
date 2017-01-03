package com.antwerkz.kibble.model

import com.antwerkz.kibble.model.Mutability.VAL
import com.antwerkz.kibble.model.Visibility.PUBLIC

class KotlinProperty(var name: String, var type: Type) : Modifiable, Visible, Mutable, Hierarchical {
    override var isAbstract: Boolean = false
    override var isFinal: Boolean = false
    override var isOpen: Boolean = false
    override var isOverride: Boolean = false
    override var visibility: Visibility = PUBLIC
    override var mutability: Mutability = VAL

    override fun toString(): String {
        return "$visibility ${if (isAbstract) "abstract " else ""}${if (isFinal) "final " else ""}${if (isOpen) "open " else ""}${if
        (isOverride) "override " else ""}$mutability $name: $type"
    }
}