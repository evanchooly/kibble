package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble
import org.testng.Assert
import org.testng.annotations.Test

@Test
class KibbleTypeTest {
    fun simpleType() {
        val file = Kibble.parseSource("val foo: com.foo.bar.Type")
        val type = file.properties[0].type

        Assert.assertEquals(type?.name, "com.foo.bar.Type")
        Assert.assertTrue(type?.typeParameters?.isEmpty() ?: false)
    }

    fun generics() {
        val string = "com.foo.bar.SomeType<kotlin.String, kotlin.Double>?"
        val type = Kibble.parseSource("val foo: $string")
                .properties[0]
                .type!!

        Assert.assertEquals(type.name, string)
        Assert.assertEquals(type.fqcn, string.substringBefore("<"))
        Assert.assertEquals(type.className, "SomeType")
        Assert.assertEquals(type.pkgName, "com.foo.bar")
        Assert.assertTrue(type.nullable)
        Assert.assertEquals(type.typeParameters.size, 2)
        Assert.assertEquals(type.typeParameters.get(0).name, "kotlin.String")
        Assert.assertEquals(type.typeParameters.get(1).name, "kotlin.Double")
    }

    fun fullyQualified() {
        val qualified = KibbleType.from("java.math.BigDecimal")
        val decimal = KibbleType.from("BigDecimal")
        val integer = KibbleType.from("BigInteger")
        val dateTime = KibbleType.from("java.time.LocalDateTime")
        val list = KibbleType("java.util.List", listOf(TypeParameter("String")))

        Assert.assertEquals(qualified.name, "java.math.BigDecimal")
        Assert.assertEquals(decimal.name, "BigDecimal")
        Assert.assertEquals(integer.name, "BigInteger")
        Assert.assertEquals(dateTime.name, "java.time.LocalDateTime")
        Assert.assertEquals(dateTime.name, "java.time.LocalDateTime")
        Assert.assertEquals(list.fqcn, "java.util.List")
        Assert.assertEquals(list.name, "java.util.List<String>")
    }

    fun components() {
        val dateTime = KibbleType.from("java.time.LocalDateTime")
        val entry = KibbleType.from("java.util.Map.Entry")
        val int = KibbleType.from("Int")

        Assert.assertEquals(dateTime.className, "LocalDateTime")
        Assert.assertEquals(dateTime.pkgName, "java.time")
        Assert.assertEquals(entry.className, "Map.Entry")
        Assert.assertEquals(entry.pkgName, "java.util")
        Assert.assertEquals(int.className, "Int")
        Assert.assertNull(int.pkgName)
    }
}