package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import org.jetbrains.kotlin.psi.KtImportDirective

/**
 * Defines an import directive in a file
 *
 * @property type the imported typed
 * @property alias the import alias
 */
data class KibbleImport internal constructor(val type: KibbleType, val alias: String? = null) : KibbleElement {

    internal constructor(kt: KtImportDirective) : this(KibbleType.from(kt.importedFqName!!.asString()), kt.aliasName)

    /**
     * @return the string/source form of this type
     */
    override fun toString() = toSource().toString()

    /**
     * @return the string/source form of this type
     */
    override fun toSource(writer: SourceWriter, level: Int): SourceWriter {
        writer.write("import ${type.name}")
        alias?.let { writer.write(" as $alias") }
        writer.writeln()

        return writer
    }
}