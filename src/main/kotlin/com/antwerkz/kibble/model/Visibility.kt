package com.antwerkz.kibble.model

/**
 * Represents the visibility of an element
 */
enum class Visibility {
    /**
     * Marks an element as having no visibility modifier.  Currently only applies to function parameters
     *
     * @see KibbleParameter
     */
    NONE,
    PUBLIC,
    PROTECTED,
    PRIVATE,
    INTERNAL;

    /**
     * @return the string/source form of this visibility modifier
     */
    override fun toString(): String {
        return name.toLowerCase()
    }
}