package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter

data class KibbleArgument(val name: String? = null, val value: Any): KibbleElement {

    constructor(value: Any):  this(null, value = value)

    override fun toSource(writer: SourceWriter, level: Int): SourceWriter {
        name?.let {
            writer.write("$name = ")
        }
        writer.write(value.toString())

        return writer
    }

    override fun toString() = toSource().toString()

    override fun collectImports(file: KibbleFile) {
    }
}
