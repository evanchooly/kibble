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
interface ClassOrObjectHolder {
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
}

internal fun ClassOrObjectHolder.extractClassesObjects(file: KibbleFile, declarations: List<KtDeclaration>) {
    declarations.filterIsInstance<KtClassOrObject>()
            .forEach {
                when (it) {
                    is KtClass -> classes += KibbleClass(file, it)
                    else -> objects += KibbleObject(file, it as KtObjectDeclaration)
                }
            }
}
