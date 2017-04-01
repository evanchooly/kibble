package com.antwerkz.kibble.model

import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtFunction

interface FunctionHolder {
    companion object {
        internal fun apply(file: KibbleFile, kt: KtClassOrObject): List<KibbleFunction> {
            return kt.getBody()?.declarations
                    ?.filterIsInstance<KtFunction>()
                    ?.map {
                        KibbleFunction(file, it)
                    } ?: listOf()

        }
    }

    val functions: MutableList<KibbleFunction>

    fun addFunction(name: String? = null, type: String = "Unit", body: String = ""): KibbleFunction
}