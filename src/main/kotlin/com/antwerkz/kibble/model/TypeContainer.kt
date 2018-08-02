package com.antwerkz.kibble.model

/**
 * Represents a type that can hold a Class or an object
 */
interface TypeContainer {
    val classes: List<KibbleClass>
    val objects: List<KibbleObject>

    /**
     * Adds a class to this container
     *
     * @param name the name of the class to add
     */
    fun addClass(name: String): KibbleClass

    /**
     * Adds a class to this container
     *
     * @param klass the class to add
     */
    fun addClass(klass: KibbleClass): KibbleClass


    /**
     * Finds the named class if it exists
     *
     * @param className the class name
     * @return the class
     */
    fun getClass(className: String): KibbleClass? {
        return classes.firstOrNull { it.name == className }
    }

    /**
     * Adds an object to this container
     *
     * @param name the name of the object to add
     * @param isCompanion true if the object should be the companion object
     */
    fun addObject(name: String, isCompanion: Boolean = false): KibbleObject

    /**
     * Adds an object to this container
     *
     * @param obj the object to add
     */
    fun addObject(obj: KibbleObject): KibbleObject

    /**
     * Finds the named object if it exists
     *
     * @param objName the object name
     * @return the object
     */
    fun getObject(objName: String): KibbleObject? {
        return objects.firstOrNull { it.name == objName }
    }
}
