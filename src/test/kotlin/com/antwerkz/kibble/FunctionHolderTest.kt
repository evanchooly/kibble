package com.antwerkz.kibble

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.LambdaTypeName
import org.intellij.lang.annotations.Language
import org.testng.Assert
import org.testng.annotations.Test

class FunctionHolderTest {

    @Test
    fun parameterizedType() {
        @Language("kotlin")
        val source = """fun nickNames(): Foo<Bar, List<String>>""".trimMargin()
        val file = Kibble.parseSource(source)
    }

    @Test
    fun expression() {
        @Language("kotlin")
        val source = """fun expression(name: String?, value: String?, userName: String?) = Foo(name ?: "")

fun body() {
    print()
    for (item in items) {
        add(item)
    }
}
"""

        val file = Kibble.parseSource(source)
        val functions = file.getFunctions("expression").iterator()
        var kibbleFunction = functions.next()
        Assert.assertEquals(kibbleFunction.body, CodeBlock.of("""Foo(name ?: "")"""))

        kibbleFunction = file.getFunctions("body").first()
        Assert.assertEquals(kibbleFunction.body, CodeBlock.of("""print()
for (item in items) {
    add(item)
}"""))
    }

    @Test
    fun functionParameter() {
        val source = """fun bloop(message: String, converter: Integer.(String, List<Double>) -> String): String {
    println()
}
"""
        val bloop = Kibble.parseSource(source)
                .functions.first()
        Assert.assertEquals(bloop.parameters[0].name, "message")

        val parameter = bloop.parameters[1]
        Assert.assertEquals(parameter.name, "converter")
        val type = parameter.type as LambdaTypeName
        val parameters = type.parameters.map { it.toString() }
        Assert.assertEquals(parameters, listOf("String", "List<Double>"))
        Assert.assertEquals(type.receiver, ClassName("", "Integer"))
        Assert.assertEquals(type.returnType, ClassName("", "String"))
    }
}