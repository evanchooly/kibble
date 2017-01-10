package com.antwerkz.kibble.model

import org.jetbrains.kotlin.psi.KtImportDirective

data class Import(val name: String, val alias: String? = null) {
    companion object {
        fun evaluate(kt: KtImportDirective): Import {
            return Import(kt.importedFqName!!.asString(), kt.aliasName)
        }
    }
}