package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter

/**
 * Defines a basic kibble element
 */
interface KibbleElement {
    /**
     * Writes this element to the given SourceWriter
     *
     * @param writer the writer to use
     * @param level the indentation level to use on the generated code
     */
    fun toSource(writer: SourceWriter = SourceWriter(), level: Int = 0): SourceWriter

    fun collectImports(file: KibbleFile)
}