package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import org.jetbrains.kotlin.psi.KtFile
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class KotlinFile(val name: String? = null,
                 var pkgName: String? = null,
                 val imports: MutableList<Import> = mutableListOf<Import>(),
                 val classes: MutableList<KotlinClass> = mutableListOf<KotlinClass>(),
                 override val functions: MutableList<KotlinFunction> = mutableListOf<KotlinFunction>()) :
        KotlinElement, FunctionHolder {

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(KotlinFile::class.java)
    }

    internal constructor(file: KtFile) : this(file.name) {
        file.declarations.forEach {
            val element = KotlinElement.evaluate(it)
            when (element) {
                is KotlinClass -> this += element
                is KotlinFunction -> this += element
                else -> LOG.warn("Unknown type being added to KotlinFile: $element")
            }
        }
        file.importDirectives.forEach {
            this += Import(it)
        }
        pkgName = file.packageDirective?.text
    }

    operator fun plusAssign(value: Import) {
        imports += value
    }

    operator fun plusAssign(kotlinClass : KotlinClass) {
        classes += kotlinClass
    }

    override fun toSource(writer: SourceWriter, indentationLevel: Int) {
        pkgName?.let {
            writer.writeln(it)
            writer.writeln()
        }
        imports.forEach { it.toSource(writer, indentationLevel) }
        if (imports.size > 0) {
            writer.writeln()
        }
        classes.forEachIndexed { i, kotlinClass ->
            kotlinClass.toSource(writer, indentationLevel)
            if ( i < classes.size ) {
                writer.writeln()
            }
        }
    }
}