package com.antwerkz.kibble.model

import com.antwerkz.kibble.SourceWriter
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.KtProperty
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class KibbleFile(val name: String? = null,
                 var pkgName: String? = null,
                 val imports: MutableList<Import> = mutableListOf<Import>(),
                 val classes: MutableList<KibbleClass> = mutableListOf<KibbleClass>(),
                 override val functions: MutableList<KibbleFunction> = mutableListOf<KibbleFunction>(),
                 override val properties: MutableList<KibbleProperty> = mutableListOf<KibbleProperty>()) :
        KibbleElement, FunctionHolder, PropertyHolder, Packaged<KibbleFile> {

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(KibbleFile::class.java)
    }

    internal constructor(file: KtFile) : this(file.name) {
        file.declarations.forEach {
            when (it) {
                is KtClass -> this += KibbleClass(this, it)
                is KtFunction -> this += KibbleFunction(this, it)
                is KtProperty -> this += KibbleProperty(this, it)
                else -> LOG.warn("Unknown type being added to KotlinFile: $it")
            }
        }
        file.importDirectives.forEach {
            this += Import(it)
        }
        pkgName = file.packageDirective?.children?.firstOrNull()?.text
    }

    override var parentClass: KibbleClass? = null

    override fun getFile(): KibbleFile {
        return this
    }

    operator fun plusAssign(value: Import) {
        imports += value
    }

    operator fun plusAssign(kibbleClass: KibbleClass) {
        classes += kibbleClass
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