package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter

class KibbleFunctionType internal constructor(name: String, var parameters: List<KibbleParameter>, val type: KibbleType?,
                                              val receiver: KibbleType?)
    : KibbleType(className = name) {

    override fun toString() = toSource().toString()

    override fun toSource(writer: SourceWriter, level: Int): SourceWriter {
        return writer {
            writeReceiver(receiver)
            writeParameters(listOf(), parameters)
            type?.let {
                    write( " -> $type")
            }
        }
    }

    override fun collectImports(file: KibbleFile) {
        super.collectImports(file)
        parameters.forEach { it.collectImports(file) }
        typeParameters.forEach { it.collectImports(file) }
    }
}