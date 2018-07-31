package com.antwerkz.kibble.model

/**
 * Represents the mode of an element
 */
enum class Modality {
    FINAL,
    SEALED,
    OPEN,
    ABSTRACT;

    /**
     * @return the string/source form of this modality modifier
     */
    override fun toString(): String {
        return name.toLowerCase()
    }

}

