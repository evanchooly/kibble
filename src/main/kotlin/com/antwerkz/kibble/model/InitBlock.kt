package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter

class InitBlock(val body: String): KibbleElement {
    override fun toSource(writer: SourceWriter, level: Int): SourceWriter {
        writer.writeln("init {", level)
        body.trimIndent()
                .lines()
                .forEach {
                    writer.writeln(it, level + 1)
                }
        writer.writeln("}", level)

        return writer
    }

    override fun collectImports(file: KibbleFile) {
    }

}
