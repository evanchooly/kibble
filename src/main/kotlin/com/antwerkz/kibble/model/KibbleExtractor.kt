package com.antwerkz.kibble.model

import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtDeclaration
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.KtObjectDeclaration
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.psi.KtSuperTypeCallEntry
import org.jetbrains.kotlin.psi.KtSuperTypeEntry
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry
import org.jetbrains.kotlin.resolve.calls.callUtil.getValueArgumentsInParentheses

internal object KibbleExtractor {
    internal fun extractClasses(kt: List<KtDeclaration>?, file: KibbleFile): MutableList<KibbleClass> {
        return (kt?.filterIsInstance<KtClass>()
                ?.map { KibbleClass(file, it) }
                ?: listOf()).toMutableList()
    }

    internal fun extractObjects(file: KibbleFile, kt: List<KtDeclaration>?): MutableList<KibbleObject> {
        return (kt?.filterIsInstance<KtObjectDeclaration>()
                ?.map { KibbleObject(file, it) }
                ?: listOf()).toMutableList()
    }

    internal fun extractFunctions(file: KibbleFile, kt: List<KtDeclaration>?): MutableList<KibbleFunction> {
        return (kt?.filterIsInstance<KtFunction>()
                ?.map { KibbleFunction(file, it) }
                ?: listOf()).toMutableList()
    }

    internal fun extractProperties(file: KibbleFile, kt: List<KtDeclaration>?): MutableList<KibbleProperty> {
        return (kt?.filterIsInstance<KtProperty>()
                ?.map { KibbleProperty(file, it) }
                ?: listOf()).toMutableList()
    }

    internal fun extractAnnotations(file: KibbleFile, kt: List<Any>?): MutableList<KibbleAnnotation> {
        return (kt?.map { KibbleAnnotation.from(file, it) }
                ?: listOf()).toMutableList()
    }

    internal fun extractSuperTypes(file: KibbleFile, kt: List<KtSuperTypeListEntry>?): MutableList<KibbleType> {
        return (kt?.filterIsInstance(KtSuperTypeEntry::class.java)
                ?.map { KibbleType.from(file, it.typeReference)!! }
                ?: listOf()).toMutableList()
    }

    internal fun extractSuperType(file: KibbleFile, kt: List<KtSuperTypeListEntry>?): KibbleType? {
        return kt?.filterIsInstance(KtSuperTypeCallEntry::class.java)
                ?.map { KibbleType.from(file, it.typeReference) }
                ?.firstOrNull()

    }

    internal fun extractSuperCallArgs(kt: List<KtSuperTypeListEntry>?): List<String> {
        return kt?.let {
            it.filterIsInstance(KtSuperTypeCallEntry::class.java)
                    .flatMap { it.getValueArgumentsInParentheses() }
                    .mapNotNull { it.getArgumentExpression()?.text }
        } ?: listOf()

    }

/*
    internal fun extractSuperInformation(file: KibbleFile, extendable: Extendable, kt: KtClassOrObject) {
        val entries = kt.superTypeListEntries

        entries.filterIsInstance(KtSuperTypeCallEntry::class.java)
                .firstOrNull()
                ?.let {
                    extendable.superType = KibbleType.from(file, it.typeReference)
                    extendable.superCallArgs = it.getValueArgumentsInParentheses()
                            .map { it.getArgumentExpression()!!.text }
                }

        extendable.superTypes = entries
                .filterIsInstance(KtSuperTypeEntry::class.java)
                .map { KibbleType.from(file, it.typeReference)!! }
    }
*/

}