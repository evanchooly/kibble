package com.antwerkz.kibble.model

import org.jetbrains.kotlin.name.parentOrNull
import org.jetbrains.kotlin.psi.KtAnnotationEntry
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtDeclaration
import org.jetbrains.kotlin.psi.KtElement
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.KtObjectDeclaration
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.psi.KtSuperTypeCallEntry
import org.jetbrains.kotlin.psi.KtSuperTypeEntry
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry
import org.jetbrains.kotlin.renderer.render
import org.jetbrains.kotlin.resolve.calls.callUtil.getValueArgumentsInParentheses

internal object KibbleExtractor {
    internal fun extractClasses(file: KibbleFile, kt: List<KtDeclaration>?): MutableList<KibbleClass> {
        return (kt?.filterIsInstance<KtClass>()
                ?.map { KibbleClass(file, it) }
                ?: listOf()).toMutableList()
    }

    internal fun extractObjects(file: KibbleFile, kt: List<KtDeclaration>?): MutableList<KibbleObject> {
        return (kt?.filterIsInstance<KtObjectDeclaration>()
                ?.map { KibbleObject(file, it) }
                ?: listOf()).toMutableList()
    }

    internal fun extractFunctions(kt: List<KtDeclaration>?): MutableList<KibbleFunction> {
        return (kt?.filterIsInstance<KtFunction>()
                ?.map { KibbleFunction(it) }
                ?: listOf()).toMutableList()
    }

    internal fun extractProperties(kt: List<KtDeclaration>?): MutableList<KibbleProperty> {
        // KotlinToJVMBytecodeCompiler
        return (kt?.filterIsInstance<KtProperty>()
                ?.map { KibbleProperty(it) }
                ?: listOf()).toMutableList()
    }

    internal fun extractAnnotations(kt: List<Any>?): MutableList<KibbleAnnotation> {
        return (kt?.filterIsInstance<KtAnnotationEntry>()
                ?.map { KibbleAnnotation.from(it) }
                ?: listOf()).toMutableList()
    }

    internal fun extractSuperTypes(kt: List<KtSuperTypeListEntry>?): MutableList<KibbleType> {
        return (kt?.filterIsInstance(KtSuperTypeEntry::class.java)
                ?.map { KibbleType.from(it.typeReference)!! }
                ?: listOf()).toMutableList()
    }

    internal fun extractSuperType(kt: List<KtSuperTypeListEntry>?): KibbleType? {
        return kt?.filterIsInstance(KtSuperTypeCallEntry::class.java)
                ?.map { KibbleType.from(it.typeReference) }
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

fun KtFile.extractPackage() = packageDirective?.children?.firstOrNull()?.text

fun KtFile.findImport(name: String): String? {
    val directive = importDirectives.find {
        val importPath = it.importPath
        val imported = name == importPath?.importedName?.render()
        val aliased = importPath?.alias?.render() == name
        imported || aliased
    }
    return directive?.importPath?.fqName?.parentOrNull()?.render()
}

fun KtElement.findFile(): KtFile {
    var next = this

    while (next !is KtFile) {
        next = next.parent as KtElement
    }

    return next
}