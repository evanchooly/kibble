package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.KtProperty
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class KotlinFile(val name: String? = null,
                 var pkgName: String? = null,
                 val imports: MutableList<Import> = mutableListOf<Import>(),
                 val classes: MutableList<KotlinClass> = mutableListOf<KotlinClass>(),
                 override val functions: MutableList<KotlinFunction> = mutableListOf<KotlinFunction>(),
                 override val properties: MutableList<KotlinProperty> = mutableListOf<KotlinProperty>()) :
        KotlinElement, FunctionHolder, PropertyHolder, Packaged<KotlinFile> {

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(KotlinFile::class.java)
    }

    internal constructor(file: KtFile) : this(file.name) {
        file.declarations.forEach {
            when (it) {
                is KtClass -> this += KotlinClass(this, it)
                is KtFunction -> this += KotlinFunction(this, it)
                is KtProperty -> this += KotlinProperty(this, it)
                else -> LOG.warn("Unknown type being added to KotlinFile: $it")
            }
        }
        file.importDirectives.forEach {
            this += Import(it)
        }
        pkgName = file.packageDirective?.children?.firstOrNull()?.text
    }

    override var parentClass: KotlinClass? = null

    override fun getFile(): KotlinFile {
        return this
    }

    operator fun plusAssign(value: Import) {
        imports += value
    }

    operator fun plusAssign(kotlinClass : KotlinClass) {
        classes += kotlinClass
    }

    override fun toSource(writer: SourceWriter, indentationLevel: Int) {
        pkgName?.let {
            writer.writeln("package $it")
            writer.writeln()
        }

        imports.forEach { it.toSource(writer, indentationLevel) }
        classes.forEach{ it.toSource(writer, indentationLevel) }
    }
}