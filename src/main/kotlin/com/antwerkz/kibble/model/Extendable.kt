package com.antwerkz.kibble.model

import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtSuperTypeCallEntry
import org.jetbrains.kotlin.psi.KtSuperTypeEntry
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry
import org.jetbrains.kotlin.resolve.calls.callUtil.getValueArgumentsInParentheses

interface Extendable {
    companion object {
        internal fun extractSuperInformation(extendable: Extendable, kt: KtClassOrObject) {
            val entries = kt.getSuperTypeListEntries()

            entries.filterIsInstance(KtSuperTypeCallEntry::class.java)
                    .firstOrNull()
                    ?.let {
                        extendable.superType = KibbleType.from(it.typeReference)
                        extendable.superCallArgs = it.getValueArgumentsInParentheses()
                                .map { it.getArgumentExpression()!!.text }

                    }
            extendable.interfaces = entries
                    .filterIsInstance(KtSuperTypeEntry::class.java)
                    .map { KibbleType.from(it.typeReference) }
        }
    }

    var superType: KibbleType?
    var superCallArgs: List<String>
    var interfaces: List<KibbleType>
}