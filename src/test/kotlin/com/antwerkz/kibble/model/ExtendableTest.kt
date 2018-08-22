package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble
import com.antwerkz.kibble.getClass
import com.squareup.kotlinpoet.ANY
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
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
        val foo = file.getClass("Foo")!!
        Assert.assertEquals(foo.superclass, ClassName("", "Bar"))
        Assert.assertEquals(foo.superclassConstructorParameters[0], CodeBlock.of("12"))
        Assert.assertNotNull(foo.superinterfaces.containsKey(ClassName("", "Runnable")))

        val baz = file.getClass("Baz")!!
        Assert.assertEquals(baz.superclass, ClassName("", "Foo"))
        Assert.assertTrue(baz.superclassConstructorParameters.isEmpty())
        Assert.assertTrue(baz.superinterfaces.isEmpty())

        val jerry = file.getClass("Jerry")!!
        Assert.assertEquals(jerry.superclass, ANY)
        Assert.assertTrue(jerry.superclassConstructorParameters.isEmpty())
        Assert.assertNotNull(jerry.superinterfaces.containsKey(ClassName("", "Runnable")))
    }
}