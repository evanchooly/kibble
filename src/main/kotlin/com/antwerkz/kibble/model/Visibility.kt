package com.antwerkz.kibble.model

enum class Visibility {
    PUBLIC,
    PROTECTED,
    PRIVATE,
    INTERNAL;

    override fun toString(): String{
        return name.toLowerCase()
    }
}