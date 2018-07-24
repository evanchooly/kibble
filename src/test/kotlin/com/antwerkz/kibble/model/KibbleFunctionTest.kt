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

        var kibbleFunction = file.functions[0]
        Assert.assertEquals("T", kibbleFunction.typeParameters[0].type?.fqcn())
        Assert.assertNull(kibbleFunction.typeParameters[0].variance)
        Assert.assertNull(kibbleFunction.typeParameters[0].bounds)

        kibbleFunction = file.functions[1]
        Assert.assertEquals("K", kibbleFunction.typeParameters[0].type?.fqcn())
        Assert.assertEquals(TypeParameterVariance.OUT, kibbleFunction.typeParameters[0].variance)
        Assert.assertEquals(KibbleType.from("Bar"), kibbleFunction.typeParameters[0].bounds)

        val foo = KibbleFunction("foo")
        foo.addParameter("t", "T")
        foo.addTypeParameter("T")

        Assert.assertEquals(foo.toString(), "fun <T> foo(t: T)")
    }
}