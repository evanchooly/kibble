package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble
import org.testng.Assert
import org.testng.annotations.Test

class KibbleFunctionTest {
    @Test
    fun varargs() {
        val file = KibbleFile()
        file.addFunction("temp", body = """return 4""")
                .addParameter("bob", "String", varargs = true)

        val source = file.toSource().toString()

        Assert.assertEquals(source, """
            fun temp(vararg bob: String) {
                return 4
            }

            """.trimIndent())
    }

    @Test
    fun generics() {
        val file = Kibble.parseSource("""fun <T> foo(t: T)
            |fun <out K: Bar> bar(k: K)
        """.trimMargin())

        var kibbleFunction = file.getFunctions("foo")[0]
        Assert.assertEquals(kibbleFunction.typeParameters[0].type?.fqcn(), "T")
        Assert.assertNull(kibbleFunction.typeParameters[0].variance)
        Assert.assertNull(kibbleFunction.typeParameters[0].bounds)

        kibbleFunction = file.getFunctions("bar")[0]
        Assert.assertEquals(kibbleFunction.typeParameters[0].type?.fqcn(), "K")
        Assert.assertEquals(kibbleFunction.typeParameters[0].variance, TypeParameterVariance.OUT)
        Assert.assertEquals(kibbleFunction.typeParameters[0].bounds, KibbleType.from("Bar"))

        val foo = file.addFunction("foo")
        foo.addParameter("t", "T")
        foo.addTypeParameter("T")

        Assert.assertEquals(foo.toString(), """fun <T> foo(t: T)
            |
        """.trimMargin())
    }
}