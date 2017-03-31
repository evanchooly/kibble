package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import com.antwerkz.kibble.StringSourceWriter
import com.antwerkz.kibble.model.Visibility.PUBLIC
import org.jetbrains.kotlin.psi.KtObjectDeclaration
import org.jetbrains.kotlin.psi.psiUtil.visibilityModifier

class KibbleObject(val parent: KibbleClass?, val name: String?, val companion: Boolean = false) :
        Annotatable,  FunctionHolder, KibbleElement, PropertyHolder, Visible {

    override var visibility: Visibility = PUBLIC
    override val functions = mutableListOf<KibbleFunction>()
    override val properties = mutableListOf<KibbleProperty>()
    override var annotations = mutableListOf<KibbleAnnotation>()

    internal constructor(parent: KibbleClass, kt: KtObjectDeclaration) : this(parent, kt.name, kt.isCompanion()) {
        visibility = Visible.apply(kt.visibilityModifier())
        kt.annotationEntries.forEach { extractAnnotation(it) }
    }

    override fun addFunction(name: String?, type: String, body: String): KibbleFunction {
        TODO("not implemented")
    }

    override fun addProperty(name: String, type: String, initializer: String?, modality: Modality, overriding: Boolean, visibility: Visibility, mutability: Mutability, lateInit: Boolean, constructorParam: Boolean): KibbleProperty {
        TODO("not implemented")
    }

    override fun toSource(writer: SourceWriter, level: Int) {
        annotations.forEach {
            writer.writeln(it.toString(), level)
        }
        writer.write(visibility.toString(), level)
        if (companion) {
            writer.write("companion ")
        }
        writer.writeln("object {")
        writer.writeln("}", level)
    }

    override fun toString() = StringSourceWriter().apply { toSource(this) }.toString()
}