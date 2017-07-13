package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble
import org.testng.Assert
import org.testng.annotations.Test

@Test
class KibbleTypeTest {
    fun simpleType() {
        val file = Kibble.parseSource("val foo: com.foo.bar.Type")
        val type = file.properties[0].type

        Assert.assertEquals(type?.fullName, "com.foo.bar.Type")
        Assert.assertEquals(type?.name, "com.foo.bar.Type")
        Assert.assertTrue(type?.typeParameters?.isEmpty() ?: false)
    }

    fun generics() {
        val string = "com.foo.bar.SomeType<kotlin.String, kotlin.Double>?"
        val file = Kibble.parseSource("val foo: $string")
        val type = file.properties[0].type!!

        Assert.assertEquals(type.fullName, string)
        Assert.assertEquals(type.name, "com.foo.bar.SomeType")
        Assert.assertTrue(type.nullable)
        Assert.assertEquals(type.typeParameters.size, 2)
        Assert.assertEquals(type.typeParameters.get(0).name, "kotlin.String")
        Assert.assertEquals(type.typeParameters.get(1).name, "kotlin.Double")
        Assert.assertEquals(type.name, "com.foo.bar.SomeType")
        Assert.assertEquals(type.fullName, string)
    }

    fun fullyQualified() {
        val qualified = KibbleType.from("java.math.BigDecimal")
        val decimal = KibbleType.from("BigDecimal")
        val integer = KibbleType.from("BigInteger")
        val dateTime = KibbleType.from("java.time.LocalDateTime")
        val list = KibbleType("java.util.List", listOf(TypeParameter("String")))

        Assert.assertEquals(qualified.fullName, "java.math.BigDecimal")
        Assert.assertEquals(decimal.fullName, "BigDecimal")
        Assert.assertEquals(integer.fullName, "BigInteger")
        Assert.assertEquals(dateTime.name, "java.time.LocalDateTime")
        Assert.assertEquals(dateTime.fullName, "java.time.LocalDateTime")
        Assert.assertEquals(list.name, "java.util.List")
        Assert.assertEquals(list.fullName, "java.util.List<String>")
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