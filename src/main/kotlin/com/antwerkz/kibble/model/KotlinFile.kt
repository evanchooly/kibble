package com.antwerkz.kibble.model

class KotlinFile : FunctionHolder {
    var pkgName: String? = null
    val imports = mutableListOf<Import>()
    val classes = mutableListOf<KotlinClass>()
    override val functions = mutableListOf<KotlinFunction>()

}