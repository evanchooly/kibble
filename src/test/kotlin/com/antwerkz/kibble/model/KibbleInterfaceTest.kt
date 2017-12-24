package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble
import org.testng.Assert
import org.testng.annotations.Test

class KibbleInterfaceTest {
    @Test
    fun basic() {
        val file = Kibble.parseSource("""interface temp {
        |}""".trimMargin())

        Assert.assertFalse(file.interfaces.isEmpty())
        Assert.assertTrue(file.classes.isEmpty())
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

        Assert.assertFalse(file.interfaces.isEmpty())
        val kibbleInterface = file.interfaces[0]
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

        Assert.assertEquals(file.toSource().toString().trim(), source.trim())

    }
}