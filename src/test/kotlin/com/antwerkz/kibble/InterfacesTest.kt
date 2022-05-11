package com.antwerkz.kibble

import com.squareup.kotlinpoet.ClassName
import org.testng.Assert
import org.testng.annotations.Test

class InterfacesTest {
    @Test
    fun basic() {
        val file = Kibble.parseSource(
            """
            interface temp {
            }
            """.trimIndent()
        )

        Assert.assertTrue(file.classes.isEmpty())
        Assert.assertFalse(file.interfaces.isEmpty())
        Assert.assertTrue(file.objects.isEmpty())
        Assert.assertTrue(file.functions.isEmpty())
        Assert.assertTrue(file.properties.isEmpty())
    }

    @Test
    fun everything() {
        val file = Kibble.parseSource(
            """
            interface temp {
                class Klass
                object Object
                fun function() 
                val property: String
            }
            """.trimIndent()
        )

        Assert.assertFalse(file.interfaces.isEmpty())
        val kibbleInterface = file.interfaces.first()
        Assert.assertFalse(kibbleInterface.classes.isEmpty())
        Assert.assertFalse(kibbleInterface.objects.isEmpty())
        Assert.assertFalse(kibbleInterface.funSpecs.isEmpty())
        Assert.assertFalse(kibbleInterface.propertySpecs.isEmpty())
    }

    @Test
    fun extends() {
        val source = """interface Temp: java.lang.Runnable {
}""".trim()
        val file = Kibble.parseSource(source)

        Assert.assertNotNull(file.interfaces.first().superinterfaces.containsKey(ClassName("java.lang", "Runnable")))
    }
}
