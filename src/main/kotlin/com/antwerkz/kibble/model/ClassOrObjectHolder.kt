package com.antwerkz.kibble.model

import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtDeclaration
import org.jetbrains.kotlin.psi.KtObjectDeclaration

/**
 * Represents a type that can hold a Class or an object
 *
 * @property classes the list of classes declared on this type
 * @property objects the list of objects declared on this type
 */
interface ClassOrObjectHolder: FunctionHolder {
    val classes: MutableList<KibbleClass>
    val objects: MutableList<KibbleObject>

    /**
     * Adds a class to this
     *
     * @param name the name of the class to add
     */
    fun addClass(name: String): KibbleClass

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
}

internal fun ClassOrObjectHolder.extractClassesObjects(file: KibbleFile, declarations: List<KtDeclaration>) {
    val classList = mutableListOf<Pair<KtClass, KibbleClass>>()
    val objectList= mutableListOf<Pair<KtObjectDeclaration, KibbleObject>>()

    declarations.filterIsInstance<KtClassOrObject>()
            .forEach {
                when (it) {
                    is KtClass -> {
                        classList += Pair(it, KibbleClass(file, it)).also {
                            classes += it.second
                        }
                    }
                    else -> objectList += Pair(it as KtObjectDeclaration, KibbleObject(file, it)).also {
                        objects += it.second
                    }
                }
            }

    classList.forEach {
        it.second.parse(it.first, file)
    }
    objectList.forEach {
        it.second.parse(it.first, file)
    }
}
