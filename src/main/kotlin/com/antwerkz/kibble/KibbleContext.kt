package com.antwerkz.kibble

import com.antwerkz.kibble.model.KibbleFile
import com.antwerkz.kibble.model.KibbleType

class KibbleContext {
    private val files = mutableMapOf<String?, MutableSet<KibbleFile>>()

    fun register(file: KibbleFile) = lookup(file.pkgName).add(file)

    fun lookup(pkgName: String?) = files.getOrPut(pkgName, { mutableSetOf() })

    fun resolve(type: KibbleType) =
        lookup(type.pkgName)
                .flatMap { it.classes }
                .firstOrNull { it.name == type.className } /*?.let {
            KibbleType(file.pkgName, type.className, type.typeParameters, type.nullable)*/
}