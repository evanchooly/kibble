package com.antwerkz.kibble.model

/**
 * Represents the visibility of an element
 *
 * @property visibility the visibility
 */
enum class Visibility {
    /**
     * Marks an element as having no visibility modifier.  Currently only applies to function parameters
     *
     * @see KibbleParameter
     */
    NONE {
        override fun toString(): String {
            return ""
        }
    },
    /**
     * Marks an element as public
     */
    PUBLIC {
        override fun toString(): String {
            return ""
        }
    },
    /**
     * Marks an element as protected
     */
    PROTECTED,
    /**
     * Marks an element as private
     */
    PRIVATE,
    /**
     * Marks an element as internal
     */
    INTERNAL;

    /**
     * @return the string/source form of this visibility modifier
     */
    override fun toString(): String {
        return name.toLowerCase() + " "
    }
}