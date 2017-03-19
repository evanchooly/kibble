package com.antwerkz.kibble.model

interface Packaged<out T> {
    var kibbleFile: KibbleFile

    fun getPackage() = kibbleFile.pkgName

    @Suppress("UNCHECKED_CAST")
    fun setPackage(name: String?): T {
        kibbleFile.pkgName = name
        return this as T
    }
}