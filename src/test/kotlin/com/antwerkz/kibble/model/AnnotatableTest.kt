package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble
import org.testng.Assert
import org.testng.annotations.Test


class AnnotatableTest {
    @Test
    fun properties() {
        val property = Kibble.parseSource("""
        @SuppressWarnings("deprecation", count=10)
        val name: String""")
                .properties[0]
        verify(property.getAnnotation(SuppressWarnings::class.java)!!)
    }

    @Test
    fun classes() {
        val klass = Kibble.parseSource("""
        @SuppressWarnings("deprecation", count=10)
        class Foo """)
                .classes[0]
        verify(klass.getAnnotation(SuppressWarnings::class.java)!!)
    }

    private fun verify(annotation: KibbleAnnotation) {
        Assert.assertEquals(annotation["value"], "\"deprecation\"")
        Assert.assertEquals(annotation.getValue(), "\"deprecation\"")
        Assert.assertEquals(annotation["count"], "10")
    }
}