package com.antwerkz.kibble.model

enum class Mutability {
    NEITHER {
        override fun toString(): String {
            return ""
        }

    },
    VAL, VAR;

    override fun toString(): String {
        return name.toLowerCase() + " "
    }
}