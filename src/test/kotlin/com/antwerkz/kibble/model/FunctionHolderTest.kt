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
        Assert.assertEquals(kibbleFunction.toString(), source.split("\n")[0])

        kibbleFunction = file.functions[1]
        Assert.assertEquals(kibbleFunction.body, """print()""")
    }
}