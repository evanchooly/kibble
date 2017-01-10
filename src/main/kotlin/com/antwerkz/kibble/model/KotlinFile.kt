package com.antwerkz.kibble.model

import org.jetbrains.kotlin.psi.KtFile

class KotlinFile(val name: String? = null) : KotlinElement, FunctionHolder {
    companion object {
        internal fun evaluate(file: KtFile): KotlinFile {
            val kotlinFile = KotlinFile(file.name)
            file.declarations.forEach {
                val element = KotlinElement.evaluate(it)
                when (element) {
                    is KotlinClass -> kotlinFile += element
                    is KotlinFunction -> kotlinFile += element
                    else -> throw IllegalArgumentException("Unknown type being added to KotlinFile: ${element}")
                }
            }
            file.importDirectives.forEach {
                kotlinFile += Import.evaluate(it)
            }

            return kotlinFile
        }
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