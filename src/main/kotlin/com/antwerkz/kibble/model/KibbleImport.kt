package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import org.jetbrains.kotlin.psi.KtImportDirective

/**
 * Defines an import directive in a file
 *
 * @property type the imported typed
 */
data class KibbleImport internal constructor(val type: KibbleType) : KibbleElement, Comparable<KibbleImport> {

    internal constructor(file: KibbleFile, kt: KtImportDirective) : this(KibbleType.from(kt))

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
        writer.write("import ${type.fqcn}")
        type.alias?.let { writer.write(" as ${type.alias}") }
        writer.writeln()

        return writer
    }
}