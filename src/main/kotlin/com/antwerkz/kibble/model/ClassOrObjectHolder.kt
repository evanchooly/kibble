package com.antwerkz.kibble.model

/**
 * Represents a type that can hold a Class or an object
 *
 * @property interfaces the list of interfaces declared on this type
 * @property classes the list of classes declared on this type
 * @property objects the list of objects declared on this type
 */
interface ClassOrObjectHolder : FunctionHolder {
    val interfaces: List<KibbleInterface>
    val classes: List<KibbleClass>
    val objects: List<KibbleObject>

    /**
     * Adds a class to this
     *
     * @param name the name of the class to add
     */
    fun addClass(name: String): KibbleClass

    /**
     * Adds a interface to this
     *
     * @param name the name of the interface to add
     */
    fun addInterface(name: String): KibbleInterface

    /**
     * Adds an object to this
     *
     * @param name the name of the object to add
     * @param isCompanion true if the object should be the companion object
     */
    fun addObject(name: String, isCompanion: Boolean = false): KibbleObject

    /**
     * Finds the named object if it exists
     *
     * @param name the object name
     * @return the object
     */
    fun getObject(objName: String): KibbleObject? {
        return objects.firstOrNull { it.name == objName }
    }

    /**
     * Finds the named class if it exists
     * 
     * @param name the class name
     * @return the class 
     */
    fun getClass(className: String): KibbleClass? {
        return classes.firstOrNull { it.name == className }
    }

    /**
     * Finds the named interface if it exists
     *
     * @param name the interface name
     * @return the interface
     */
    fun getInterface(interfaceName: String): KibbleInterface? {
        return interfaces.firstOrNull { it.name == interfaceName }
    }
}