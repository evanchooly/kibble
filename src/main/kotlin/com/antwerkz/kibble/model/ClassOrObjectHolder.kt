package com.antwerkz.kibble.model

import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtObjectDeclaration

interface ClassOrObjectHolder {
    companion object {
        internal fun apply(file: KibbleFile, kt: KtClassOrObject, parent: KibbleClass? = null): Pair<List<KibbleClass>,
                List<KibbleObject>> {
            var classes = listOf<KibbleClass>()
            var objects = listOf<KibbleObject>()
            kt.getBody()?.declarations
                    ?.filterIsInstance<KtClassOrObject>()
                    ?.forEach {
                        when (it) {
                            is KtClass -> classes += KibbleClass(file, it)
                            else -> objects += KibbleObject(file, parent, it as KtObjectDeclaration)
                        }
                    }
            return Pair(classes, objects)

        }
    }

    val nestedClasses: MutableList<KibbleClass>
    val objects: MutableList<KibbleObject>
}