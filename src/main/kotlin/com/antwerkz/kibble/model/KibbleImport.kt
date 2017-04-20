package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import org.jetbrains.kotlin.psi.KtImportDirective

data class KibbleImport internal constructor(val type: KibbleType, val alias: String? = null) : KibbleElement {

    internal constructor(kt: KtImportDirective) : this(KibbleType.from(kt.importedFqName!!.asString()), kt.aliasName)

    override fun toString() = toSource().toString()

    override fun toSource(writer: SourceWriter, level: Int): SourceWriter {
        writer.write("import ${type.fullName}")
        alias?.let { writer.write(" as $alias") }
        writer.writeln()

        return writer
    }
}