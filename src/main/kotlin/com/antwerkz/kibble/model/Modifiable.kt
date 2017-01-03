package com.antwerkz.kibble.model

interface Modifiable {
    fun addModifier(modifier: String) {
        when (modifier) {
            "public", "protected", "private", "internal" -> {
                this as Visible
                visibility = Visibility.valueOf(modifier.toUpperCase())
            }
            "final" -> {
                this as Hierarchical
                isFinal = true
            }
            "abstract" -> {
                this as Hierarchical
                isAbstract = true
            }
            "open" -> {
                this as Hierarchical
                isOpen = true
            }
            "override" -> {
                this as Hierarchical
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