package com.antwerkz.kibble

import com.squareup.kotlinpoet.FileSpec
import org.slf4j.LoggerFactory
import java.util.Stack

@Suppress("UNCHECKED_CAST")
class KibbleContext {
    companion object {
        val LOG = LoggerFactory.getLogger(KibbleContext::class.java)!!
    }

    private data class Bookmark(val name: String)

    val autoImports = mutableSetOf(
            "Any",
            "Unit",
            "Nothing",
            "Byte",
            "Short",
            "Int",
            "Long",
            "Float",
            "Double",
            "Boolean",
            "String",
            "Integer",
            "List",
            "Map",
            "String",
            "MutableList",
            "MutableMap",
            "MutableString",
            "Suppress"
    )

    val files = mutableListOf<FileSpec>()

    internal val stack = Stack<Any>()

    var defaultPackageName = ""

    fun register(file: FileSpec) = files.add(file)

    fun push(value: Any) {
/*
        if (value !is Slab &&
                value !is SuperCall &&
                value !is CallBlock &&
                value !is TypeProjection &&
                value !is Pair<*, *> &&
                !value::class.java.`package`.name.startsWith("com.square")) {
            throw Exception("non-kotlinpoet type pushed to the stack: [${value::class.java}] $value")
        }
*/
        stack.push(value)
    }

    fun <T> pop(): T = stack.pop() as T

    fun <T> peek(): T = (if (!stack.isEmpty()) stack.peek() else null) as T

    fun <T> apply(body: T.() -> Unit) {
        peek<T>()?.body()
    }

    fun bookmark(name: String) {
        push(Bookmark(name))
        LOG.debug("bookmarking $name")
    }

    fun popToBookmark(): List<Any> {
        val values = mutableListOf<Any>()
        while (peek<Any>() !is Bookmark) {
            values += pop<Any>()
        }
        val slab = pop<Bookmark>()
        LOG.debug("removing bookmark $slab")
        return values.reversed()
    }

    fun isAutoImport(name: String) = name in autoImports
    override fun toString() = if (!stack.isEmpty()) peek<Any>().toString() else "{}"
}
