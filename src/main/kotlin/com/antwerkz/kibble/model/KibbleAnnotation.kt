package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter

/**
 * Represents an annotation in kotlin source code.
 *
 * @property type the annotation type
 * @property arguments the values passed to the annotation
 */
class KibbleAnnotation internal constructor(val type: KibbleType, val arguments: List<KibbleArgument> = listOf()) : KibbleElement {

    /**
     * @return the source form of this annotation
     */
    override fun toString() = toSource().toString()

    override fun toSource(writer: SourceWriter, level: Int): SourceWriter {
        var string = "@$type"
        if (arguments.isNotEmpty()) {
            string += arguments.joinToString(prefix = "(", postfix = ")", transform = { it.toSource(SourceWriter(), level).toString() })
        }
        writer.write(string, level)
        return writer
    }

    /**
     * @return the value of the named parameter for the annotation
     */
    operator fun get(name: String): Any? {
        return arguments.firstOrNull { it.name == name || (it.name == null && name == "value")}
                ?.value
    }

    /**
     * @return the value of the "value" parameter for the annotation
     */
    fun getAnnotationValue(name: String): KibbleAnnotation? {
        return this[name] as KibbleAnnotation?
    }

    /**
     * @return the value of the "value" parameter for the annotation
     */
    fun getValue(): Any? {
        return arguments.firstOrNull { it.name == null || it.name == "value" }
                        ?.value
    }

    override fun collectImports(file: KibbleFile) {
        file.resolve(type)
    }
}