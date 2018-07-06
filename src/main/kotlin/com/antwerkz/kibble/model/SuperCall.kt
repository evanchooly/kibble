package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter

class SuperCall(val type: KibbleType, val arguments: MutableList<KibbleArgument> = mutableListOf()): KibbleElement {
    override fun toSource(writer: SourceWriter, level: Int): SourceWriter {
        TODO("not implemented")
    }

    override fun collectImports(file: KibbleFile) {
        TODO("not implemented")
    }

}