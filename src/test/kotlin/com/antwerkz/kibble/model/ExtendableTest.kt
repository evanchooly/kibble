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
        val foo = file.classes[0]
        Assert.assertEquals(foo.superType?.name, "Bar")
        Assert.assertEquals(foo.superCallArgs[0], "12")
        Assert.assertEquals(foo.interfaces[0].name, "Runnable")

        val baz = file.classes[1]
        Assert.assertEquals(baz.superType?.name, "Foo")
        Assert.assertTrue(baz.superCallArgs.isEmpty())
        Assert.assertTrue(baz.interfaces.isEmpty())

        val jerry = file.classes[2]
        Assert.assertNull(jerry.superType)
        Assert.assertTrue(jerry.superCallArgs.isEmpty())
        Assert.assertEquals(jerry.interfaces[0].name, "Runnable")
    }
}