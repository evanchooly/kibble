package com.antwerkz.kibble.model

import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtSuperTypeCallEntry
import org.jetbrains.kotlin.psi.KtSuperTypeEntry
import org.jetbrains.kotlin.resolve.calls.callUtil.getValueArgumentsInParentheses

/**
 * Represents a type that can be extended.
 *
 * @property file the KibbleFile this type belongs to
 * @property superType the parent class of this type
 * @property superCallArgs the arguments to pass to the constructor of the parent class
 * @property superTypes the types this type extends:  classes and interfaces alike
 */
interface Extendable {
    companion object {
        internal fun extractSuperInformation(extendable: Extendable, kt: KtClassOrObject) {
            val entries = kt.superTypeListEntries

            entries.filterIsInstance(KtSuperTypeCallEntry::class.java)
                    .firstOrNull()
                    ?.let {
                        extendable.superType = KibbleType.from(extendable.file, it.typeReference)
                        extendable.superCallArgs = it.getValueArgumentsInParentheses()
                                .map { it.getArgumentExpression()!!.text }
                    }

            extendable.superTypes = entries
                    .filterIsInstance(KtSuperTypeEntry::class.java)
                    .map { KibbleType.from(extendable.file, it.typeReference)!! }
        }
    }

    val file: KibbleFile
    var superType: KibbleType?
    var superCallArgs: List<String>
    var superTypes: List<KibbleType>
}