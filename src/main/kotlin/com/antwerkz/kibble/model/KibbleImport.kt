package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import org.jetbrains.kotlin.psi.KtImportDirective

data class KibbleImport internal constructor(val name: String, val alias: String? = null): KibbleElement {
    internal constructor(kt: KtImportDirective): this(kt.importedFqName!!.asString(), kt.aliasName)

    override fun toSource(writer: SourceWriter, indentationLevel: Int) {
        writer.write("import $name")
        alias?.let { writer.write(" as $alias")}
        writer.writeln()
    }
}