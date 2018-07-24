package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import org.jetbrains.kotlin.psi.KtImportDirective

/**
 * Defines an import directive in a file
 *
 * @property type the imported typed
 */
data class KibbleImport internal constructor(val type: KibbleType,  val alias: String? = null) : KibbleElement, Comparable<KibbleImport> {

    init {
        type.resolved = alias ?: type.className
    }

    override fun compareTo(other: KibbleImport): Int {
        return type.compareTo(other.type)
    }

    /**
     * @return the string/source form of this type
     */
    override fun toString() = toSource().toString()

    /**
     * @return the string/source form of this type
     */
    override fun toSource(writer: SourceWriter, level: Int): SourceWriter {
        writer.write("import ${type.fqcn()}")
        alias?.let { writer.write(" as $it") }
        writer.writeln()

        return writer
    }

    override fun collectImports(file: KibbleFile) {}
}