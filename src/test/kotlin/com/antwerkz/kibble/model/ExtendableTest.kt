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
        Assert.assertEquals(foo.extends?.className, "Bar")
        Assert.assertEquals(foo.superCallArgs[0].value, "12")
//        Assert.assertEquals(foo.parentInterfaces[0].className, "Runnable")

        val baz = file.classes[1]
        Assert.assertEquals(baz.extends?.className, "Foo")
        Assert.assertTrue(baz.superCallArgs.isEmpty())
//        Assert.assertTrue(baz.parentInterfaces.isEmpty())

        val jerry = file.classes[2]
        Assert.assertNull(jerry.extends)
        Assert.assertTrue(jerry.superCallArgs.isEmpty())
//        Assert.assertEquals(jerry.parentInterfaces[0].className, "Runnable")
    }
}