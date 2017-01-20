package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import com.antwerkz.kibble.model.Modality.FINAL
import com.antwerkz.kibble.model.Visibility.PUBLIC
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtSecondaryConstructor
import org.jetbrains.kotlin.psi.getOrCreateBody
import org.jetbrains.kotlin.psi.psiUtil.modalityModifier
import org.jetbrains.kotlin.psi.psiUtil.visibilityModifier

class KotlinClass(var name: String? = null,
                  override var modality: Modality = FINAL,
                  override val functions: MutableList<KotlinFunction> = mutableListOf<KotlinFunction>(),
                  override var visibility: Visibility = PUBLIC,

                  var constructor: Constructor? = null,
                  val secondaries: MutableList<SecondaryConstructor> = mutableListOf<SecondaryConstructor>(),
                  var parent: KotlinClass? = null,
                  val nestedClasses: MutableList<KotlinClass> = mutableListOf<KotlinClass>(),
                  val properties: MutableList<KotlinProperty> = mutableListOf<KotlinProperty>()
) : KotlinElement, FunctionHolder, Visible, Hierarchical {
    internal constructor(kt: KtClass): this(kt.name) {
            addModifier(kt.modalityModifier()?.text)
            addModifier(kt.visibilityModifier()?.text)
            kt.getPrimaryConstructor()?.let {
                constructor = Constructor(it)
            }
            constructor?.parameters
                    ?.filter { it.mutability != null }
                    ?.forEach {
                        val kotlinProperty = KotlinProperty(it.name, it.type, it.defaultValue, lateInit = false)
                        kotlinProperty.ctorParam = true
                        this += kotlinProperty
                    }
            kt.getSecondaryConstructors().forEach {
                this += SecondaryConstructor(it)
            }
            kt.getOrCreateBody().declarations
                    .filter { it !is KtSecondaryConstructor }
                    .forEach {
                        KotlinElement.evaluate(it)?.let { element ->
                            when (element) {
                                is KotlinClass -> this += element
                                is KotlinFunction -> this += element
                                is KotlinProperty -> this += element
                                else -> throw IllegalArgumentException("Unknown type being added to this: $element")
                            }
                        }
                    }
    }

    operator fun plusAssign(ctor: SecondaryConstructor) {
        secondaries += ctor
    }

    operator fun plusAssign(property: KotlinProperty) {
        properties += property
    }

    operator fun plusAssign(nested: KotlinClass) {
        nested.parent = this
        nestedClasses += nested
    }

    override fun toString(): String {
        return "class $name"
    }

    override fun toSource(writer: SourceWriter, indentationLevel: Int) {
        writer.writeIndent(indentationLevel)
        writer.write("$visibility${modality}class ")
        name?.let { writer.write(it) }
        constructor?.toSource(writer, indentationLevel)
        writer.writeln(" {")
        properties.filter { !it.ctorParam }
                .forEach { it.toSource(writer, indentationLevel + 1) }
        functions.forEach { it.toSource(writer, indentationLevel + 1) }
        writer.writeln("}")
    }
}

