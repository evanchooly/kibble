package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble
import org.testng.Assert
import org.testng.annotations.Test


class AnnotatableTest {
    val annotation = """@SuppressWarnings("deprecation", count=10, foo=@Foo(42))"""
    @Test
    fun properties() {
        val property = Kibble.parseSource("""
        $annotation
        val name: String""")
                .properties[0]
        Assert.assertTrue(property.hasAnnotation(SuppressWarnings::class.java))
        verify(property.getAnnotation(SuppressWarnings::class.java)!!)
    }

    @Test
    fun classes() {
        val klass = Kibble.parseSource("""
        $annotation
        class Foo """)
                .classes[0]
        Assert.assertTrue(klass.hasAnnotation(SuppressWarnings::class.java))
        verify(klass.getAnnotation(SuppressWarnings::class.java)!!)
    }

    private fun verify(annotation: KibbleAnnotation) {
        Assert.assertEquals(annotation["value"], "\"deprecation\"")
        Assert.assertEquals(annotation.getValue(), "\"deprecation\"")
        Assert.assertEquals(annotation["count"], "10")
        Assert.assertEquals(annotation.getAnnotationValue("foo")?.getValue(), "42")
    }
}