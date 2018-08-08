package com.antwerkz.kibble.model

import com.antwerkz.kibble.KibbleContext

/**
 * Represents a type that can hold a Class or an object
 */
interface TypeContainer {
    val context: KibbleContext
    val classes: List<KibbleClass>
    val objects: List<KibbleObject>

    /**
     * Adds a class to this container
     *
     * @param name the name of the class to add
     */
    fun addClass(name: String): KibbleClass {
        return addClass(KibbleClass(name, context = context))
    }

    /**
     * Adds a class to this container
     *
     * @param klass the class to add
     */
    fun addClass(klass: KibbleClass): KibbleClass


    /**
     * Finds the named class if it exists
     *
     * @param name the class name
     * @return the class
     */
    fun getClass(name: String): KibbleClass {
        return classes.find { it.name == name } ?: throw IllegalArgumentException("class not found")
    }

    /**
     * Adds an object to this container
     *
     * @param name the name of the object to add
     * @param isCompanion true if the object should be the companion object
     */
    fun addObject(name: String, isCompanion: Boolean = false): KibbleObject {
        return addObject(KibbleObject(name, isCompanion, context))
    }

    /**
     * Adds an object to this container
     *
     * @param obj the object to add
     */
    fun addObject(obj: KibbleObject): KibbleObject

    /**
     * Finds the named object if it exists
     *
     * @param name the object name
     * @return the object
     */
    fun getObject(name: String): KibbleObject? {
        return objects.find { it.name == name } ?: throw IllegalArgumentException("object not found")
    }
}
