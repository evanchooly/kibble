package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import com.antwerkz.kibble.model.Modality.FINAL
import com.antwerkz.kibble.model.Mutability.VAL
import com.antwerkz.kibble.model.Visibility.PUBLIC

/**
 * Defines a property on a file, class, or object
 *
 * @property name the name of the property
 * @property type the type of the property
 * @property initializer any initialization expression for the property
 * @property lateInit true if the property should have the `lateinit` modifier
 * @property overriding true if this property is overriding a property in a parent type
 * @property constructorParam true if the property should be listed as a constructor parameter
 */
class KibbleProperty internal constructor(name: String, type: KibbleType?, initializer: String? = null,
                                          override var modality: Modality = FINAL, override var overriding: Boolean = false,
                                          var lateInit: Boolean = false, var constructorParam: Boolean = false)
    : KibbleParameter(name, type, initializer), Visible, Mutable, Modal<KibbleProperty>, Overridable, AnnotationHolder {

    init {
        visibility = PUBLIC
        mutability = VAL
    }

    override val annotations: MutableList<KibbleAnnotation> = mutableListOf()

    override fun toSource(writer: SourceWriter, level: Int): SourceWriter {
        writer {

            annotations.forEach { it.toSource(writer, level) }

            writeIndent(level)
            write(visibility)
            write(modality)
            if (overriding) write("override ")
            if (lateInit) write("lateinit ")
            write(mutability)
            name?.let { write(name) }
            writeType(type)
            writeInitializer(initializer)
            if(!constructorParam) {
                writeln()
            }
        }

        return writer
    }

    override fun toString(): String {
        return toSource().toString().trim()
    }
}

