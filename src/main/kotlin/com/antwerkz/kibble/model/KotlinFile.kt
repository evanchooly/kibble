package com.antwerkz.kibble.model

import org.jetbrains.kotlin.psi.KtFile
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class KotlinFile(val name: String? = null) : KotlinElement, FunctionHolder {
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

    var pkgName: String? = null
    val imports = mutableListOf<Import>()
    val classes = mutableListOf<KotlinClass>()
    override val functions = mutableListOf<KotlinFunction>()

    operator fun plusAssign(value: Import) {
        imports += value
    }

    operator fun plusAssign(kotlinClass : KotlinClass) {
        classes += kotlinClass
    }
}