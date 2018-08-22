package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble
import com.antwerkz.kibble.classes
import com.antwerkz.kibble.secondaries
import org.testng.Assert
import org.testng.annotations.Test
import kotlin.test.fail

class ConstructorTest {
    @Test
    fun constructors() {
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

}
