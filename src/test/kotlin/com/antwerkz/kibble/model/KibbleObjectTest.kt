package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble
import com.antwerkz.kibble.classes
import com.antwerkz.kibble.companion
import com.antwerkz.kibble.objects
import com.squareup.kotlinpoet.ANY
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier.OVERRIDE
import com.squareup.kotlinpoet.KModifier.PRIVATE
import org.testng.Assert
import org.testng.annotations.Test

class KibbleObjectTest {
    @Test
    fun objects() {
        val file = Kibble.parse("src/test/resources/com/antwerkz/test/SampleKibbleObject.kt")

        val objects = file.classes.first().objects.iterator()

        val kibbleObject = objects.next()
        Assert.assertTrue(kibbleObject.isCompanion)
        Assert.assertTrue(kibbleObject.modifiers.contains(PRIVATE))
        Assert.assertEquals(kibbleObject.superclass, ANY)
        Assert.assertTrue(kibbleObject.superclassConstructorParameters.isEmpty())
        Assert.assertNotNull(kibbleObject.superinterfaces.containsKey(ClassName("java.lang", "Runnable")))
        val functions = kibbleObject.funSpecs.iterator()

        var function = functions.next()
        Assert.assertEquals(function.name, "run")
        Assert.assertTrue(function.modifiers.contains(OVERRIDE))

        function = functions.next()
        Assert.assertEquals(function.name, "dummy")
    }

    @Test
    fun functions() {
        val obj = Kibble.parseSource("""
object temp {
    fun something(): Junk {
        println("something")
    }
}""".trim()).objects.first()

        Assert.assertEquals(obj.name, "temp")
        val function = obj.funSpecs.first()
        Assert.assertEquals(function.name, "something")
        Assert.assertEquals(function.returnType, ClassName("", "Junk"))
    }
}