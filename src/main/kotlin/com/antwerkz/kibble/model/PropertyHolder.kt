package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble
import com.antwerkz.kibble.model.KibbleType.Companion.from

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
     * @param property the property declaration
     * @param type the type of the property
     *
     * @return the new property
     */
    fun addProperty(name: String, type: Class<*>, init: KibbleProperty.() -> Unit = {}): KibbleProperty {
        val property = KibbleProperty(name, from(type))
        property.init()
        return addProperty(property)
    }

    /**
     * Adds new property to this type
     *
     * @param property the property declaration
     * @param type the type of the property
     *
     * @return the new property
     */
    fun addProperty(name: String, type: String, init: KibbleProperty.() -> Unit = {}): KibbleProperty {
        val property = KibbleProperty(name, from(type))
        property.init()
        return addProperty(property)
    }

    /**
     * Adds new property to this type
     *
     * @param property the property declaration
     *
     * @return the new property
     */
    fun addProperty(property: String): KibbleProperty {
        return try {
            addProperty(Kibble.parseSource(property).properties[0])
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid value for a property", e)
        }
    }

    fun addProperty(property: KibbleProperty): KibbleProperty

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