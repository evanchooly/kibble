package com.antwerkz.kibble

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier.LATEINIT
import org.intellij.lang.annotations.Language
import org.testng.Assert
import org.testng.annotations.Test

class KibblePropertyTest {
    @Test
    fun lateinit() {
        val file = Kibble.parseSource(
            """
    lateinit var foo: Int = 42
    var bar: Int = 42
"""
        )
        val iterator = file.properties.iterator()
        var prop = iterator.next()
        Assert.assertEquals(prop.name, "foo")
        Assert.assertTrue(prop.modifiers.contains(LATEINIT))

        prop = iterator.next()
        Assert.assertEquals(prop.name, "bar")
        Assert.assertFalse(prop.modifiers.contains(LATEINIT))
    }

    @Test
    fun implicitType() {
        val file = Kibble.parseSource("var foo = 42")
        val iterator = file.properties.iterator()
        val prop = iterator.next()
        Assert.assertEquals(prop.name, "foo")
        Assert.assertEquals(prop.type, ClassName("", ""))
        Assert.assertFalse(prop.modifiers.contains(LATEINIT))
    }

    @Test
    fun generics() {
        @Language("kotlin")
        val fqcn = """import com.foo.Bar
val list: List<Bar>"""
        val file = Kibble.parseSource(fqcn)
        val list = file.properties[0]
        Assert.assertEquals(list.type.toString(), "List<com.foo.Bar>")
//        Assert.assertEquals(list.type.?.size, 1)
//        val typeParameter: TypeParameter = list.type?.typeParameters!![0]
    }
}
