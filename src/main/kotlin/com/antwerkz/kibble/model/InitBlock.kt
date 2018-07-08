package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter

class InitBlock(val body: String): KibbleElement {
    override fun toSource(writer: SourceWriter, level: Int): SourceWriter {
        writer.write("init {", level)
        writer.write(body)
        writer.write("}", level)

        return writer
    }

    override fun collectImports(file: KibbleFile) {
    }

}
