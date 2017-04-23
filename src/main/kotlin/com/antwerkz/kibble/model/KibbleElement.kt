package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtDeclaration
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.KtProperty
import org.slf4j.Logger
import org.slf4j.LoggerFactory

interface KibbleElement {
    /**
     * Writes this element to the given SourceWriter
     *
     * @param writer the writer to use
     * @param indent the indentation level to use on the generated code
     */
    fun toSource(writer: SourceWriter = SourceWriter(), level: Int = 0): SourceWriter
}