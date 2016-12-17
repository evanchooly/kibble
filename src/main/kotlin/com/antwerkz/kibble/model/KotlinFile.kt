package com.antwerkz.kibble.model

class KotlinFile {
    var pkgName: String? = null
    val imports = mutableListOf<Import>()
    val classes = mutableListOf<KotlinClass>()
}