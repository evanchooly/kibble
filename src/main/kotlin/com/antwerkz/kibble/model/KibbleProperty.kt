package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import com.antwerkz.kibble.model.Modality.FINAL
import com.antwerkz.kibble.model.Mutability.VAL
import com.antwerkz.kibble.model.Mutability.VAR
import com.antwerkz.kibble.model.Visibility.PUBLIC
import org.jetbrains.kotlin.psi.KtParameter
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.psi.psiUtil.allChildren
import org.jetbrains.kotlin.psi.psiUtil.modalityModifier
import org.jetbrains.kotlin.psi.psiUtil.visibilityModifier

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
    : KibbleParameter(name, type, initializer), Visible, Mutable, Modal<KibbleProperty>, Overridable, Annotatable {

    init {
        visibility = PUBLIC
        mutability = VAL
    }

    internal constructor(file: KibbleFile, kt: KtParameter) : this(kt.name!!, KibbleType.from(kt.typeReference),
            kt.defaultValue?.text) {

        extractAnnotations(file, kt.annotationEntries)
        modality = Modal.apply(kt.modalityModifier())
        visibility = Visible.apply(kt.visibilityModifier())
        mutability = Mutable.apply(kt.valOrVarKeyword)
    }

    internal constructor(file: KibbleFile, kt: KtProperty) : this(kt.name!!, KibbleType.from(kt.typeReference),
            kt.initializer?.text) {

        extractAnnotations(file, kt.annotationEntries)
        modality = Modal.apply(kt.modalityModifier())
        visibility = Visible.apply(kt.visibilityModifier())
        lateInit = kt.modifierList?.allChildren?.find { it.text == "lateinit" } != null

        if (kt.isVar || lateInit) {
            mutability = VAR
        }
    }

    override var annotations: MutableList<KibbleAnnotation> = mutableListOf()

    override fun toSource(writer: SourceWriter, level: Int): SourceWriter {
        annotations.forEach {
            writer.writeln(it.toString(), level)
        }

        writer.write(visibility.toString(), level)
        writer.write(modality.toString())
        if (overriding) {
            writer.write("override ")
        }
        if (lateInit) {
            writer.write("lateinit ")
        }
        writer.write(mutability.toString())
        writer.write(name)
        type?.let { writer.write(": $it") }
        initializer?.let { writer.write(" = $it") }
        writer.writeln()

        return writer
    }

    override fun toString(): String {
        return toSource().toString().trim()
    }
}

