package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble
import org.testng.Assert
import org.testng.annotations.Test

class KibbleInterfaceTest {
    @Test
    fun basic() {
        val file = Kibble.parseSource("""interface temp {
        |}""".trimMargin())

        Assert.assertTrue(file.classes.any { it.isInterface })
        Assert.assertFalse(file.classes.any { !it.isInterface })
        Assert.assertTrue(file.objects.isEmpty())
        Assert.assertTrue(file.functions.isEmpty())
        Assert.assertTrue(file.properties.isEmpty())
    }

    @Test
    fun everything() {
        val file = Kibble.parseSource("""interface temp {
        |
        |class Klass
        |
        |object Object
        |
        |fun function() 
        |
        |val property: String
        |
        |}""".trimMargin())

        Assert.assertTrue(file.classes.any { it.isInterface })
        val kibbleInterface = file.classes.first { it.isInterface }
        Assert.assertFalse(kibbleInterface.classes.isEmpty())
        Assert.assertFalse(kibbleInterface.objects.isEmpty())
        Assert.assertFalse(kibbleInterface.functions.isEmpty())
        Assert.assertFalse(kibbleInterface.properties.isEmpty())
    }

    @Test
    fun extends() {
        val source = """interface Temp: java.lang.Runnable {
}""".trim()
        val file = Kibble.parseSource(source)

        Assert.assertEquals(file.classes[0].implements[0].fqcn(), "java.lang.Runnable")

    }
}