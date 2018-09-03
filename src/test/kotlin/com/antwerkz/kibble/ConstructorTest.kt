package com.antwerkz.kibble

import com.squareup.kotlinpoet.KModifier
import org.testng.Assert
import org.testng.annotations.Test

class ConstructorTest {
    @Test
    fun secondaries() {
        val classes = Kibble.parseSource("""
class Factory(val type: String) {
    constructor(): this("car")
}
        """).classes.iterator()

        val klass = classes.next()
        Assert.assertEquals(klass.primaryConstructor?.parameters?.size, 1)
        Assert.assertEquals("type", klass.propertySpecs[0].name)

        val secondaries = klass.secondaries.iterator()
        val secondary = secondaries.next()
        Assert.assertNotNull(secondary)
        Assert.assertTrue(secondary.parameters.isEmpty())
    }

    @Test
    fun constructors() {
        val fileSpec = Kibble.parseSource(
                """
class Factory(vararg val type: String = "red")
        """
        )
        val classes = fileSpec.classes.iterator()

        val klass = classes.next()
        val primaryConstructor = klass.primaryConstructor!!
        Assert.assertEquals(primaryConstructor.parameters.size, 1)

        val parameterSpec = primaryConstructor.parameters[0]
        Assert.assertEquals(parameterSpec.name, "type")
        Assert.assertEquals(parameterSpec.defaultValue.toString(), "\"red\"")
        Assert.assertTrue(KModifier.VARARG in parameterSpec.modifiers)
        Assert.assertEquals(klass.propertySpecs[0].name, "type")

        fileSpec.writeTo(System.out)
    }
}
