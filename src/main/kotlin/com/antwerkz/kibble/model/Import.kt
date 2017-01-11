package com.antwerkz.kibble.model

import org.jetbrains.kotlin.psi.KtImportDirective

data class Import(val name: String, val alias: String? = null) {
    internal constructor(kt: KtImportDirective): this(kt.importedFqName!!.asString(), kt.aliasName)
}