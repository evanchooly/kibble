package com.antwerkz.kibble

import com.antwerkz.kibble.model.KibbleFile
import com.antwerkz.kibble.model.KibbleType
import java.util.Stack

@Suppress("UNCHECKED_CAST")
class KibbleContext {
    private val files = mutableMapOf<String?, MutableSet<KibbleFile>>()

    private val stack = Stack<Any>()
            
    fun register(file: KibbleFile) = lookup(file.pkgName).add(file)

    fun lookup(pkgName: String?) = files.getOrPut(pkgName, { mutableSetOf() })

    fun resolve(type: KibbleType) =
        lookup(type.pkgName)
                .flatMap { it.classes }
                .firstOrNull { it.name == type.className }

    fun fileList(): List<KibbleFile> = files.values
            .flatMap { it.toList() }
            .also {
                it.forEach {file ->
                    file.collectImports()
                }
            }

    fun push(value: Any) {
        stack.push(value)
    }

    fun <T> pop(): T = stack.pop() as T

    fun <T> peek(): T = (if (!stack.isEmpty()) stack.peek() else null) as T

    override fun toString() = if (!stack.isEmpty()) peek<Any>().toString() else "{}"
}
