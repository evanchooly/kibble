package com.antwerkz.kibble.model

enum class Visibility {
    PUBLIC {
        override fun toString(): String {
            return ""
        }
    },
    PROTECTED,
    PRIVATE,
    INTERNAL;

    override fun toString(): String {
        return name.toLowerCase() + " "
    }
}