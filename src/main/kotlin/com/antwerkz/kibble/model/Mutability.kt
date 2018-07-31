package com.antwerkz.kibble.model

/**
 * Specifies the mutability of the decorated element
 */
enum class Mutability {
    /**
     * Marks an element as neither `val` nor `var`.  Currently only applicable to function parameters
     *
     * @see KibbleParameter
     */
    NEITHER,
    /**
     * Indicates an element is decorated as a `val`
     */
    VAL,
    /**
     * Indicates an element is decorated as a `var`
     */
    VAR;

    /**
     * @return the string/source form of this mutability modifier
     */
    override fun toString(): String {
        return name.toLowerCase()
    }
}