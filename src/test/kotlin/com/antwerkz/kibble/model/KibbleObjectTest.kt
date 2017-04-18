package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble
import org.testng.Assert
import org.testng.annotations.Test

class KibbleObjectTest {
    @Test
    fun objects() {
        val file = Kibble.parseFile("src/test/resources/com/antwerkz/test/SampleKibbleObject.kt")

        val objects = file.classes[0].objects.iterator()

        val kibbleObject = objects.next()
        Assert.assertTrue(kibbleObject.companion)
        Assert.assertTrue(kibbleObject.isPrivate())
        Assert.assertNull(kibbleObject.superType)
        Assert.assertTrue(kibbleObject.superCallArgs.isEmpty())
        Assert.assertEquals(kibbleObject.superTypes[0].name, "Runnable")
        val functions = kibbleObject.functions.iterator()

        var function = functions.next()
        Assert.assertEquals(function.name, "run")
        Assert.assertTrue(function.overriding)

        function = functions.next()
        Assert.assertEquals(function.name, "dummy")
    }
}