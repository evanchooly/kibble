package com.antwerkz.kibble.model

import com.antwerkz.kibble.model.Mutability.VAR
import com.antwerkz.kibble.model.Mutability.valueOf
import com.antwerkz.kibble.model.Visibility.INTERNAL
import com.antwerkz.kibble.model.Visibility.PRIVATE
import com.antwerkz.kibble.model.Visibility.PROTECTED
import com.antwerkz.kibble.model.Visibility.PUBLIC
import com.antwerkz.kibble.model.Visibility.valueOf

class KotlinClass() : FunctionHolder, Modifiable, Visible {
    lateinit var name: String
    override var visibility = PUBLIC
    val constructors = mutableListOf<KotlinFunction>()
    override val functions = mutableListOf<KotlinFunction>()
}

interface Modifiable {
    fun addModifier(modifier: String) {
        when (modifier) {
            "public", "protected", "private", "internal" -> {
                this as Visible
                visibility = Visibility.valueOf(modifier.toUpperCase())
            }
            "override" -> {
                this as Overridable
                isOverride = true
            }
            "var", "val" -> {
                this as Mutable
                mutability = Mutability.valueOf(modifier.toUpperCase())
            }
            else -> {
                throw IllegalStateException("Unknown modifier: $modifier")
            }
        }
    }
}

enum class Visibility {
    PUBLIC,
    PROTECTED,
    PRIVATE,
    INTERNAL
}

enum class Mutability {
    VAL, VAR
}

interface Mutable {
    var mutability: Mutability
}

interface Visible {
    var visibility: Visibility
}

