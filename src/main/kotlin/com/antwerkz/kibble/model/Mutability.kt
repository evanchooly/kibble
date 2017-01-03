package com.antwerkz.kibble.model

enum class Mutability {
    VAL, VAR;

    override fun toString(): String{
        return name.toLowerCase()
    }
}