package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble
import com.antwerkz.kibble.model.KibbleType.Companion.from
import org.intellij.lang.annotations.Language
import org.testng.Assert
import org.testng.annotations.Test

@Test
class KibbleTypeTest {
    fun simpleType() {
        val type = KibbleType.from("com.foo.bar.Type")

        Assert.assertEquals(type.toSource().toString(), "Type")
        Assert.assertTrue(type.typeParameters.isEmpty())
    }

    fun generics() {
        val string = "com.foo.bar.SomeType<String, Double>?"
        val type = KibbleType.from(string)

        Assert.assertEquals(type.toSource().toString(), "SomeType<String, Double>?")
        Assert.assertEquals(type.className, "SomeType")
        Assert.assertEquals(type.pkgName, "com.foo.bar")
        Assert.assertTrue(type.nullable)
        Assert.assertEquals(type.typeParameters.size, 2)
        Assert.assertEquals(type.typeParameters[0].type?.toSource().toString(), "String")
        Assert.assertEquals(type.typeParameters[1].type?.toSource().toString(), "Double")

        val kibbleClass = Kibble.parseSource("class Foo<T: Runnable")
                .classes.first()
        Assert.assertEquals(kibbleClass.typeParameters[0].bounds?.toSource().toString(), "Runnable")
        Assert.assertEquals(kibbleClass.typeParameters[0].type?.toSource().toString(), "T")
    }

    @Test
    fun externalize() {
        val string = "java.util.List<com.foo.Bar>"
        val type = KibbleType.from(string)
        Assert.assertEquals(type.toSource().toString(), "List<Bar>")
        Assert.assertEquals(type.externalize(), string)
    }

    fun fullyQualified() {
        Assert.assertEquals(from("java.math.BigDecimal").fqcn(), "java.math.BigDecimal")
        Assert.assertEquals(from("java.math.BigDecimal").toSource().toString(), "BigDecimal")
        Assert.assertEquals(from("BigDecimal").fqcn(), "BigDecimal")
        Assert.assertEquals(from("BigInteger").fqcn(), "BigInteger")
        Assert.assertEquals(from("java.time.LocalDateTime").fqcn(), "java.time.LocalDateTime")
        Assert.assertEquals(from("java.time.LocalDateTime").toSource().toString(), "LocalDateTime")
        Assert.assertEquals(KibbleType("java.util", "List").also {
            it.addTypeParameters(listOf(TypeParameter(from("String"))))
        }.toSource().toString(), "java.util.List<String>")
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
        val type = file.resolve(KibbleType("this.is.the.package", "Class", true).also {
            it.addTypeParameters(listOf(TypeParameter(from("K")), TypeParameter(from("V"))))
        })

        Assert.assertEquals(type.fqcn(), "this.is.the.package.Class")
        Assert.assertEquals(type.toSource().toString(), "Class<K, V>?")

        val list = file.resolve(from("java.util.List<com.foo.Bar, out K>"))
        Assert.assertEquals(list.fqcn(), "java.util.List")
        Assert.assertEquals(list.toSource().toString(), "List<Bar, out K>")
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
        val props = file.classes.first().properties.iterator()

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