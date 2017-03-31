package com.antwerkz.kibble.model

import com.antwerkz.kibble.model.KibbleClass.Companion
import org.jetbrains.kotlin.psi.KtSuperTypeCallEntry
import org.jetbrains.kotlin.psi.KtSuperTypeEntry
import org.jetbrains.kotlin.psi.KtSuperTypeList
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry
import org.jetbrains.kotlin.resolve.calls.callUtil.getValueArgumentsInParentheses

interface Extendable {
    companion object {
        internal fun extractSuperInformation(extendable: Extendable, superTypeListEntries: List<KtSuperTypeListEntry>) {
            val superCall = superTypeListEntries.filterIsInstance(KtSuperTypeCallEntry::class.java)
                    .firstOrNull()
            superCall?.let {
                extendable.superType = KibbleType.from(it.typeReference)
                extendable.superCallArgs = it.getValueArgumentsInParentheses().map { arg ->
                    arg.getArgumentExpression()!!.text
                }

            }
            extendable.interfaces = superTypeListEntries
                    .filterIsInstance(KtSuperTypeEntry::class.java)
                    .map { KibbleType.from(it.typeReference) }
        }
    }

    var superType: KibbleType?
    var superCallArgs: List<String>
    var interfaces: List<KibbleType>
}