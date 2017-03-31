package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import org.jetbrains.kotlin.psi.KtImportDirective

data class KibbleImport internal constructor(val name: String, val alias: String? = null) : KibbleElement {

    constructor(type: Class<*>, alias: String? = null) : this(type.name, alias)

    internal constructor(kt: KtImportDirective) : this(kt.importedFqName!!.asString(), kt.aliasName)

    override fun toSource(writer: SourceWriter, level: Int) {
        writer.write("import $name")
        alias?.let { writer.write(" as $alias") }
        writer.writeln()
    }
}