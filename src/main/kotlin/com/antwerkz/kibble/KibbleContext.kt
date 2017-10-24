package com.antwerkz.kibble

import com.antwerkz.kibble.model.KibbleClass
import com.antwerkz.kibble.model.KibbleFile
import com.antwerkz.kibble.model.KibbleType

class KibbleContext {
    private val files = mutableMapOf<String?, MutableSet<KibbleFile>>()

    fun register(file: KibbleFile) = lookup(file.pkgName).add(file)

    fun lookup(pkgName: String?) = files.getOrPut(pkgName, { mutableSetOf() })

    fun resolve(file: KibbleFile, type: KibbleType): KibbleType? {
        val found = lookup(file.pkgName)
                .flatMap { it.classes }
                .firstOrNull { it.name == type.className }
        val let = found?.let {
            KibbleType(file, type.className, file.pkgName, type.typeParameters, type.nullable, type.alias, true)
        }
        return let
    }

    fun resolve(file: KibbleFile, type: String): KibbleType? {
        val found = lookup(file.pkgName)
                .flatMap { it.classes }
                .firstOrNull { it.name == type }
        return found?.let {
            KibbleType(file, "${file.pkgName}.$type")
            KibbleType(file, type, file.pkgName, imported = true)
        }
    }

    fun findClass(type: KibbleType): KibbleClass? {
        return lookup(type.pkgName)
                .flatMap { it.classes }
                .firstOrNull { it.name == type.className }
    }
}