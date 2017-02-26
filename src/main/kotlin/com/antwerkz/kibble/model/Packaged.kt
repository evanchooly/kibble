package com.antwerkz.kibble.model

interface Packaged<out T> {
    fun getFile(): KotlinFile

    fun getPackage() = getFile().pkgName

    @Suppress("UNCHECKED_CAST")
    fun setPackage(name: String?): T {
        getFile().pkgName = name
        return this as T
    }
}