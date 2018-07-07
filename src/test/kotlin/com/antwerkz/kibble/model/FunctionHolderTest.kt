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
}
"""

        val file = Kibble.parseSource(source)
        var kibbleFunction = file.functions[0]
        Assert.assertEquals(kibbleFunction.body, """Foo(name ?: "")""")

        kibbleFunction = file.functions[1]
        Assert.assertEquals(kibbleFunction.body, """    print()""")
    }

    @Test
    fun functionParameter() {
        val bloop = Kibble.parseSource("""fun bloop(message: String, converter: (String, List<Double>) -> String): String { }""")
                .functions[0]
        Assert.assertEquals(bloop.parameters[0].name, "message")
        Assert.assertEquals(bloop.parameters[0].type.toString(), "String")

        val kibbleParameter = bloop.parameters[1]
        Assert.assertEquals(kibbleParameter.name, "converter")
        val type = kibbleParameter.type as KibbleFunctionType
        Assert.assertEquals(type.parameters.map { it.toString() }, listOf("String", "List<Double>"))
        Assert.assertEquals(type.type.toString(), "String")
    }
}