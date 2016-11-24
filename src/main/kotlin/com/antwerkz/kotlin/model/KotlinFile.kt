package com.antwerkz.kotlin.model

class KotlinFile {
    var pkgName: String? = null
    val imports = mutableListOf<Import>()
    val classes = mutableListOf<KotlinClass>()
}