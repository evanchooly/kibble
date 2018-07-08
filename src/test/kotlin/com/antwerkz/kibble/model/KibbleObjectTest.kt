package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble
import org.testng.Assert
import org.testng.annotations.Test

class KibbleObjectTest {
    @Test
    fun objects() {
        val file = Kibble.parse("src/test/resources/com/antwerkz/test/SampleKibbleObject.kt")

        val objects = file.classes[0].objects.iterator()

        val kibbleObject = objects.next()
        Assert.assertTrue(kibbleObject.companion)
        Assert.assertTrue(kibbleObject.isPrivate())
        Assert.assertNull(kibbleObject.extends)
        Assert.assertTrue(kibbleObject.superCallArgs.isEmpty())
        Assert.assertEquals(kibbleObject.implements[0].className, "Runnable")
        val functions = kibbleObject.functions.iterator()

        var function = functions.next()
        Assert.assertEquals(function.name, "run")
        Assert.assertTrue(function.overriding)

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
}""".trim()).objects[0]

        Assert.assertEquals(obj.name, "temp")
        val function = obj.functions[0]
        Assert.assertEquals(function.name, "something")
        Assert.assertEquals(function.type, KibbleType(className = "Junk"))

        val file = KibbleFile("temp.kt")

        val temp = file.addObject("temp")
        temp.addFunction("something", "Junk", """println("something")""")

        Assert.assertEquals(temp, obj)
    }
}