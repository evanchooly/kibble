package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble
import org.intellij.lang.annotations.Language
import org.testng.Assert
import org.testng.annotations.Test

class FunctionHolderTest {
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
        Assert.assertEquals(kibbleFunction.body, """Foo(name ?: "")""")

        kibbleFunction = file.getFunctions("body").first()
        Assert.assertEquals(kibbleFunction.body, """print()
for (item in items) {
    add(item)
}""")
    }

    @Test
    fun functionParameter() {
        val source = """fun bloop(message: String, converter: (String, List<Double>) -> String): String {
    println()
}
"""
        val bloop = Kibble.parseSource(source)
                .functions.first()
        Assert.assertEquals(bloop.parameters[0].name, "message")
        Assert.assertEquals(bloop.parameters[0].type?.toSource().toString(), "String")

        val kibbleParameter = bloop.parameters[1]
        Assert.assertEquals(kibbleParameter.name, "converter")
        val type = kibbleParameter.type as KibbleFunctionType
        val list = type.parameters.map { it.type?.toSource().toString() }
        Assert.assertEquals(list, listOf("String", "List<Double>"))
        Assert.assertEquals(type.type?.toSource().toString(), "String")
        Assert.assertEquals(bloop.toSource().toString(), source)
    }
}