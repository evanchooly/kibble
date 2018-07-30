package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble

/**
 * Represents a type than hold properties
 *
 * @property properties the properties held by this type
 */
interface PropertyHolder {
    val properties: MutableList<KibbleProperty>

    /**
     * Adds new property to this type
     *
     * @param property the property name
     *
     * @return the new property
     */
    fun addProperty(property: String): KibbleProperty {
        return try {
            Kibble.parseSource(property).properties.first().also { properties += it }
        } catch(e: Exception) {
            throw IllegalArgumentException("Invalid value for a property", e)
        }
    }

/*
     * Adds new property to this type
     *
     * @param name the property name
     * @param type the property type
     * @param initializer the property initializer
     * @param modality the property modality
     * @param overriding the property overriding
     * @param visibility the property visibility
     * @param mutability the property mutability
     * @param lateInit true if the property should have the `lateinit` modifier
     * @param constructorParam true if the property should should be listed as a constructor parameter
     *
     * @return the new property

    fun addProperty(name: String, type: String? = null,
                    initializer: String? = null,
                    modality: Modality = FINAL,
                    overriding: Boolean = false,
                    visibility: Visibility = PUBLIC,
                    mutability: Mutability = VAL,
                    lateInit: Boolean = false,
                    constructorParam: Boolean = false): KibbleProperty
*/

    /**
     * Gets a property by name if it exists
     *
     * @param prop the property name
     * @return the property
     */
    fun getProperty(prop: String): KibbleProperty? {
        return properties.firstOrNull { it.name == prop }
    }
}