package com.antwerkz.kibble

import com.antwerkz.kibble.Kibble.parseSource
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier.LATEINIT
import org.intellij.lang.annotations.Language
import org.testng.Assert
import org.testng.annotations.Test

class KibblePropertyTest {
    @Test
    fun setters() {
        fun validate(file: FileSpec) {
            val iterator = file.properties.iterator()
            val prop = iterator.next()
            Assert.assertEquals(prop.name, "bar")
            Assert.assertNull(prop.getter)
            Assert.assertNotNull(prop.setter)
        }
        validate(
            parseSource(
                """
        internal var bar: Int = 42
            set(value: Int) {
                field = value
            }
    """
            )
        )
/*  no type inference support
        validate(
            parseSource(
                """
        var bar: Int = 42
            set(value) {
                field = value
            }
    """
            )
        )
*/
    }

    @Test
    fun lateinit() {
        val file = parseSource(
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
    fun importedTypes() {
        val file = parseSource(
            """
            package somepackage.name
            
            import com.what.the.What
            
            var bar: MutableList<Int> = mutableListOf() 
            var foo: Blargle
            var baz: What
            """.trimIndent()
        )
        val iterator = file.properties.iterator()
        var prop = iterator.next()
        Assert.assertEquals(prop.name, "bar")
        Assert.assertEquals(prop.type.toString(), "kotlin.collections.MutableList<kotlin.Int>")
        prop = iterator.next()
        Assert.assertEquals(prop.name, "foo")
        Assert.assertEquals(prop.type.toString(), "somepackage.name.Blargle")
        prop = iterator.next()
        Assert.assertEquals(prop.name, "baz")
        Assert.assertEquals(prop.type.toString(), "com.what.the.What")
    }

    @Test
    fun implicitType() {
        val file = parseSource("var foo = 42")
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
        val file = parseSource(fqcn)
        val list = file.properties[0]
        Assert.assertEquals(list.type.toString(), "kotlin.collections.List<com.foo.Bar>")
//        Assert.assertEquals(list.type.?.size, 1)
//        val typeParameter: TypeParameter = list.type?.typeParameters!![0]
    }
}
