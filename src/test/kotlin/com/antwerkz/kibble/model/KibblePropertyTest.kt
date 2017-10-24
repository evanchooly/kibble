package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble
import org.intellij.lang.annotations.Language
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

    @Test
    fun implicitType() {
        val file = Kibble.parseSource("var foo = 42")
        val iterator = file.properties.iterator()
        val prop = iterator.next()
        Assert.assertEquals(prop.name, "foo")
        Assert.assertNull(prop.type)
        Assert.assertFalse(prop.lateInit)
    }

    @Test
    fun generics() {
        @Language("kotlin")
        var source = """val foo: Prop<T>"""
        Assert.assertEquals(Kibble.parseSource(source).properties[0].toSource().toString().trim(), source)

        @Language("kotlin")
        val source2 = """val foo: Prop<*>"""
        Assert.assertEquals(Kibble.parseSource(source2).properties[0].toSource().toString().trim(), source2)

        @Language("kotlin")
        val fqcn = """import com.foo.Bar
val list: List<Bar>"""
        val kibbleFile = Kibble.parseSource(fqcn)
        val list = kibbleFile.properties[0]
        Assert.assertEquals(list.type?.className, "List")
        Assert.assertEquals(list.type?.typeParameters?.size, 1)
        val typeParameter: TypeParameter = list.type?.typeParameters!![0]
        Assert.assertEquals(typeParameter.type.fqcn, "com.foo.Bar")
        Assert.assertEquals(typeParameter.type.pkgName, "com.foo")
        Assert.assertEquals(typeParameter.type.className, "Bar")
    }
}