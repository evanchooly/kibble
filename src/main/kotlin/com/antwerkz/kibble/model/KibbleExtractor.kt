package com.antwerkz.kibble.model

import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtDeclaration
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.KtObjectDeclaration
import org.jetbrains.kotlin.psi.KtProperty

internal object KibbleExtractor {
    internal fun extractClasses(kt: List<KtDeclaration>?, file: KibbleFile): MutableList<KibbleClass> {
        return (kt?.filterIsInstance<KtClass>()
                ?.map { KibbleClass(file, it) }
                ?: listOf()).toMutableList()
    }

    internal fun extractObjects(kt: List<KtDeclaration>?, file: KibbleFile): MutableList<KibbleObject> {
        return (kt?.filterIsInstance<KtObjectDeclaration>()
                ?.map { KibbleObject(file, it) }
                ?: listOf()).toMutableList()
    }

    internal fun extractFunctions(kt: List<KtDeclaration>?, file: KibbleFile): MutableList<KibbleFunction> {
        return (kt?.filterIsInstance<KtFunction>()
                ?.map { KibbleFunction(file, it) }
                ?: listOf()).toMutableList()
    }

    internal fun extractProperties(kt: List<KtDeclaration>?, file: KibbleFile): MutableList<KibbleProperty> {
        return (kt?.filterIsInstance<KtProperty>()
                ?.map { KibbleProperty(file, it) }
                ?: listOf()).toMutableList()
    }
}