package com.antwerkz.kibble.model

import org.jetbrains.kotlin.psi.KtDeclaration
import org.jetbrains.kotlin.psi.KtFunction

interface FunctionHolder {
    val functions: MutableList<KibbleFunction>

    fun addFunction(name: String? = null, type: String = "Unit", body: String = ""): KibbleFunction
}

internal fun FunctionHolder.extractFunctions(file: KibbleFile, declarations: List<KtDeclaration>) {
    functions += declarations
            .filterIsInstance<KtFunction>()
            .map {
                KibbleFunction(file, it)
            }
}
