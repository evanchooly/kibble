package com.antwerkz.kibble.model

import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtDeclaration
import org.jetbrains.kotlin.psi.KtObjectDeclaration

interface ClassOrObjectHolder {
    val classes: MutableList<KibbleClass>
    val objects: MutableList<KibbleObject>

    fun addClass(name: String): KibbleClass
    fun addObject(name: String, isCompanion: Boolean = false): KibbleObject
}

internal fun ClassOrObjectHolder.extractClassesObjects(file: KibbleFile, declarations: List<KtDeclaration>, parent: KibbleClass? = null) {
    declarations.filterIsInstance<KtClassOrObject>()
            .forEach {
                when (it) {
                    is KtClass -> classes += KibbleClass(file, it)
                    else -> objects += KibbleObject(file, parent, it as KtObjectDeclaration)
                }
            }
}
