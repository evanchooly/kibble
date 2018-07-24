package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble
import com.antwerkz.kibble.model.KibbleType.Companion.from
import org.intellij.lang.annotations.Language
import org.testng.Assert
import org.testng.annotations.Test

@Test
class KibbleTypeTest {
    fun simpleType() {
        val file = Kibble.parseSource("val foo: com.foo.bar.Type")
        val type = file.properties[0].type

        Assert.assertEquals(type?.toString(), "com.foo.bar.Type")
        Assert.assertTrue(type?.typeParameters?.isEmpty() ?: false)
    }

    fun generics() {
        val string = "com.foo.bar.SomeType<kotlin.String, kotlin.Double>?"
        val type = Kibble.parseSource("val foo: $string")
                .properties[0]
                .type!!

        Assert.assertEquals(type.toString(), "com.foo.bar.SomeType<kotlin.String, kotlin.Double>?")
        Assert.assertEquals(type.className, "SomeType")
        Assert.assertEquals(type.pkgName, "com.foo.bar")
        Assert.assertTrue(type.nullable)
        Assert.assertEquals(type.typeParameters.size, 2)
        Assert.assertEquals(type.typeParameters[0].type.toString(), "kotlin.String")
        Assert.assertEquals(type.typeParameters[1].type.toString(), "kotlin.Double")

        val kibbleClass = Kibble.parseSource("class Foo<T: Runnable")
                .classes[0]
        Assert.assertEquals(kibbleClass.typeParameters[0].bounds.toString(), "Runnable")
        Assert.assertEquals(kibbleClass.typeParameters[0].type.toString(), "T")
    }

    fun fullyQualified() {

        Assert.assertEquals(from("java.math.BigDecimal").toString(), "java.math.BigDecimal")
        Assert.assertEquals(from("BigDecimal").toString(), "BigDecimal")
        Assert.assertEquals(from("BigInteger").toString(), "BigInteger")
        Assert.assertEquals(from("java.time.LocalDateTime").toString(), "java.time.LocalDateTime")
        Assert.assertEquals(KibbleType("java.util", "List", mutableListOf(TypeParameter(from("String")))).toString(), "java.util.List<String>")
    }

    fun components() {
        val dateTime = from("java.time.LocalDateTime")
        val entry = from("java.util.Map.Entry")
        val int = from("Int")

        Assert.assertEquals(dateTime.className, "LocalDateTime")
        Assert.assertEquals(dateTime.pkgName, "java.time")
        Assert.assertEquals(entry.className, "Map.Entry")
        Assert.assertEquals(entry.pkgName, "java.util")
        Assert.assertEquals(int.className, "Int")
        Assert.assertNull(int.pkgName)
    }

    fun values() {
        val file = KibbleFile(pkgName = "com.antwerkz.aliases")
        val type = file.resolve(KibbleType("this.is.the.package", "Class",
                mutableListOf(TypeParameter(from("K")),
                TypeParameter(from("V"))), true))

        Assert.assertEquals(type.fqcn(), "this.is.the.package.Class")
        Assert.assertEquals(type.toString(), "Class<K, V>?")

        val list = file.resolve(from("java.util.List<com.foo.Bar, out K>"))
        Assert.assertEquals(list.fqcn(), "java.util.List")
        Assert.assertEquals(list.toString(), "List<Bar, out K>")
    }

    fun autoImportedTypes() {
        @Language("kotlin")
        val source = """package com.antwerkz.testing
class Main {
    val b: Boolean
    val byte: Byte
    val short: Short
    val d: Double
    val f: Float
    val l: Long
    val i: Int
    val integer: Integer
    val s: String
}
""".trim()
        val file = Kibble.parseSource(source)
        val props = file.classes[0].properties.iterator()

        check(props.next(), "Boolean")
        check(props.next(), "Byte")
        check(props.next(), "Short")
        check(props.next(), "Double")
        check(props.next(), "Float")
        check(props.next(), "Long")
        check(props.next(), "Int")
        check(props.next(), "Integer")
        check(props.next(), "String")
    }

    private fun check(property: KibbleProperty, fqcn: String) {
        val type = property.type!!
        Assert.assertEquals(type.fqcn(), fqcn)
    }
}