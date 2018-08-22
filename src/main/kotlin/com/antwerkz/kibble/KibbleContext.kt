package com.antwerkz.kibble

import com.antwerkz.kibble.model.CallBlock
import com.antwerkz.kibble.model.TypeProjection
import com.antwerkz.kibble.model.SuperCall
import com.squareup.kotlinpoet.FileSpec
import org.slf4j.LoggerFactory
import java.util.Stack

@Suppress("UNCHECKED_CAST")
class KibbleContext {
    companion object {
        val LOG = LoggerFactory.getLogger(KibbleContext::class.java)

        private val list = listOf(
                "kotlin",
                "kotlin.collections",
                "kotlin.io",
                "kotlin.jvm",
                "kotlin.comparisons",
                "kotlin.text",
                "kotlin.sequences",
                "kotlin.ranges",
                "java.lang",
                "kotlin.annotation"
        )
    }
    private data class Slab(val name: String)

    val autoImports = mutableSetOf<String>("Any", "Unit", "Nothing", "Byte", "Short", "Int", "Long", "Float", "Double",
                    "Boolean", "String", "Integer", "List", "Map", "String", "MutableList", "MutableMap", "MutableString", "Suppress")

    val files = mutableListOf<FileSpec>()

    private val stack = Stack<Any>()

    var defaultPackageName = ""

    fun register(file: FileSpec) = files.add(file)

    fun push(value: Any) {
        if(value !is Slab
                && value !is SuperCall
                && value !is CallBlock
                && value !is TypeProjection
                && value !is Pair<*, *>
                && !value::class.java.`package`.name.startsWith("com.square")) {
            throw Exception("non-kotlinpoet type pushed to the stack: [${value::class.java}] $value")
        }
        stack.push(value)
    }

    fun <T> pop(): T = stack.pop() as T

    fun <T> peek(): T = (if (!stack.isEmpty()) stack.peek() else null) as T

    fun bookmark(name: String) {
        push(Slab(name))
        LOG.debug("bookmarking $name")
    }

    fun popToBookmark(): List<Any> {
        val values = mutableListOf<Any>()
        while (peek<Any>() !is Slab) {
            values += pop<Any>()
        }
        val slab = pop<Slab>()
        LOG.debug("removing bookmark $slab")
        return values.reversed()
    }

    fun isAutoImport(name: String): Boolean {
        return name in autoImports
/*
        for(packageName in list) {
            try {
                val fqcn = "$packageName.$name"
                val resourceAsStream = javaClass.getResourceAsStream("/${fqcn.replace('.', '/')}.class")
                println("Looking for `$fqcn`")
                val forName = Class.forName(fqcn)
                println("forName = $forName")
                autoImports += name
                return true
            } catch(ignored: ClassNotFoundException) {
//                ignored.printStackTrace()
            }
        }
*/

    }

    override fun toString() = if (!stack.isEmpty()) peek<Any>().toString() else "{}"
}
