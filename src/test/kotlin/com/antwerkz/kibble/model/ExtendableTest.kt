package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble
import org.testng.Assert
import org.testng.annotations.Test

class ExtendableTest {
    @Test
    fun parentClass() {
        val file = Kibble.parseSource("""
    class Foo: Bar(12), Runnable

    class Baz: Foo()

    class Jerry: Runnable
""")
        val foo = file.getClass("Foo")
        Assert.assertEquals(foo.extends?.className, "Bar")
        Assert.assertEquals(foo.superCallArgs[0].value, "12")
        Assert.assertEquals(foo.implements[0].className, "Runnable")

        val baz = file.getClass("Baz")
        Assert.assertEquals(baz.extends?.className, "Foo")
        Assert.assertTrue(baz.superCallArgs.isEmpty())
        Assert.assertTrue(baz.implements.isEmpty())

        val jerry = file.getClass("Jerry")
        Assert.assertNull(jerry.extends)
        Assert.assertTrue(jerry.superCallArgs.isEmpty())
        Assert.assertEquals(jerry.implements[0].className, "Runnable")
    }
}