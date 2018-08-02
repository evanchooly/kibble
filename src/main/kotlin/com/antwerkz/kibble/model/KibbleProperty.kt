package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import com.antwerkz.kibble.model.Modality.FINAL

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
class KibbleProperty internal constructor(val name: String, val type: KibbleType?, var initializer: String? = null,
                                          override var modality: Modality = FINAL, override var overriding: Boolean = false,
                                          var lateInit: Boolean = false, var constructorParam: Boolean = false)
    : Visible, Mutable, Modal<KibbleProperty>, Overridable, AnnotationHolder,
        KibbleElement, GenericCapable {

    override var visibility = Visibility.PUBLIC
    override var mutability= Mutability.VAL
    override var typeParameters = listOf<TypeParameter>()
        private set
    override var annotations= listOf<KibbleAnnotation>()
        private set

    override fun addTypeParameter(type: TypeParameter) {
        typeParameters += type
    }

    override fun collectImports(file: KibbleFile) {
        type?.let { file.resolve(it) }
        annotations.forEach { it.collectImports(file) }
        typeParameters.forEach { it.collectImports(file) }
    }

    override fun toSource(writer: SourceWriter, level: Int): SourceWriter {
        writer {

            annotations.forEach { it.toSource(writer, level) }

            writeIndent(level)
            write(visibility)
            write(modality)
            if (overriding) write("override ")
            if (lateInit) write("lateinit ")
            write(mutability)
            write(name)
            writeType(type)
            writeInitializer(initializer)
            if (!constructorParam) {
                writeln()
            }
        }

        return writer
    }

    override fun toString(): String {
        return toSource().toString().trim()
    }

    override fun addAnnotation(annotation: KibbleAnnotation) {
        annotations += annotation
    }
}

