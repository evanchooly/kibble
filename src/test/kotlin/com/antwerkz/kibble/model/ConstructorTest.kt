package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble
import org.testng.Assert
import org.testng.annotations.Test

class ConstructorTest {
    @Test
    fun constructors() {
        val classes = Kibble.parseSource("""
class Factory(val type: String) {
    constructor(): this("car")
}
        """).classes.iterator()

        val klass = classes.next()
        Assert.assertEquals("type", klass.constructor.parameters[0].name)
        Assert.assertEquals("type", klass.properties[0].name)

        val secondaries = klass.secondaries.iterator()
        val secondary = secondaries.next()
        Assert.assertNotNull(secondary)
        Assert.assertTrue(secondary.parameters.isEmpty())
    }
}
