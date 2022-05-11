package com.antwerkz.kibble

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier
import org.testng.Assert
import org.testng.annotations.Test

class KibbleFunctionTest {
    @Test
    fun generics() {
        val file = Kibble.parseSource(
            """
            fun <T> foo(t: T)
            fun <out K: Bar> bar(k: K)
            """.trimIndent()
        )
        var kibbleFunction = file.getFunctions("foo")[0]
        Assert.assertEquals(kibbleFunction.typeVariables[0].toString(), "T")
        Assert.assertNull(kibbleFunction.typeVariables[0].variance)
        Assert.assertTrue(kibbleFunction.typeVariables[0].bounds.isEmpty(), kibbleFunction.typeVariables[0].toString())

        kibbleFunction = file.getFunctions("bar")[0]
        Assert.assertEquals(kibbleFunction.typeVariables[0].toString(), "K")
        Assert.assertEquals(kibbleFunction.typeVariables[0].variance, KModifier.OUT)
        Assert.assertEquals(kibbleFunction.typeVariables[0].bounds, listOf(ClassName("", "Bar")))
    }
}
