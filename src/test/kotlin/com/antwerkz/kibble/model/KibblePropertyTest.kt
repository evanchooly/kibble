package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble
import org.testng.Assert
import org.testng.annotations.Test

class KibblePropertyTest {
    @Test
    fun lateinit() {
        val file = Kibble.parseSource("""
    lateinit var foo: Int = 42
    var bar: Int = 42
""")
        val iterator = file.properties.iterator()
        var prop = iterator.next()
        Assert.assertEquals(prop.name, "foo")
        Assert.assertTrue(prop.lateInit)

        prop = iterator.next()
        Assert.assertEquals(prop.name, "bar")
        Assert.assertFalse(prop.lateInit)
    }
}