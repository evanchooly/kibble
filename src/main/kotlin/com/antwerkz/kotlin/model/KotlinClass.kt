package com.antwerkz.kotlin.model

import com.antwerkz.kotlin.model.Visibility.INTERNAL
import com.antwerkz.kotlin.model.Visibility.PRIVATE
import com.antwerkz.kotlin.model.Visibility.PROTECTED
import com.antwerkz.kotlin.model.Visibility.PUBLIC

class KotlinClass() : Modifiable, Visible {
    lateinit var name: String
    override var visibility = PUBLIC
    val constructors = mutableListOf<KotlinFunction>()
    val functions = mutableListOf<KotlinFunction>()
}

interface Modifiable {
    fun addModifier(modifier: String) {
        when (modifier) {
            "public" -> {
                this as Visible
                visibility = PUBLIC
            }
            "protected" -> {
                this as Visible
                visibility = PROTECTED
            }
            "private" -> {
                this as Visible
                visibility = PRIVATE
            }
            "internal" -> {
                this as Visible
                visibility = INTERNAL
            }
            "override" -> {
                this as Overridable
                isOverride = true
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

interface Visible {
    var visibility: Visibility
}

