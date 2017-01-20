package com.antwerkz.kibble.model

enum class Modality {
    FINAL {
        override fun toString(): String {
            return ""
        }
    },
    SEALED,
    OPEN,
    ABSTRACT;

    override fun toString(): String {
        return name.toLowerCase() + " "
    }

}

