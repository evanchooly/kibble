package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble

/**
 * Represents a type than hold properties
 *
 * @property properties the properties held by this type
 */
interface PropertyHolder {
    val properties: List<KibbleProperty>

    /**
     * Adds new property to this type
     *
     * @param property the property name
     *
     * @return the new property
     */
    fun addProperty(property: String): KibbleProperty {
        return try {
            Kibble.parseSource(property).properties
                    .first()
                    .also {
                        addProperty(it)
                    }
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid value for a property", e)
        }
    }

    fun addProperty(property: KibbleProperty)

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