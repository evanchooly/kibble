package com.antwerkz.kibble.model

/**
 * Represents the mode of an element
 *
 * @property modality the mode
 */
enum class Modality {
    /**
     * @return true if this element is final
     */
    FINAL {
        override fun toString(): String {
            return ""
        }
    },
    /**
     * @return true if this element is sealed
     */
    SEALED,
    /**
     * @return true if this element is open
     */
    OPEN,
    /**
     * @return true if this element is abstract
     */
    ABSTRACT;

    /**
     * @return the string/source form of this modality modifier
     */
    override fun toString(): String {
        return name.toLowerCase() + " "
    }

}

