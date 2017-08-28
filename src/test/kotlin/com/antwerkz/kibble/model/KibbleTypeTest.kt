package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble
import org.testng.Assert
import org.testng.annotations.Test

@Test
class KibbleTypeTest {
    fun simpleType() {
        val file = Kibble.parseSource("val foo: com.foo.bar.Type")
        val type = file.properties[0].type

        Assert.assertEquals(type?.value, "Type")
        Assert.assertTrue(type?.typeParameters?.isEmpty() ?: false)
    }

    fun generics() {
        val string = "com.foo.bar.SomeType<kotlin.String, kotlin.Double>?"
        val type = Kibble.parseSource("val foo: $string")
                .properties[0]
                .type!!

        Assert.assertEquals(type.value, "SomeType<kotlin.String, kotlin.Double>?")
        Assert.assertEquals(type.className, "SomeType")
        Assert.assertEquals(type.pkgName, "com.foo.bar")
        Assert.assertTrue(type.nullable)
        Assert.assertEquals(type.typeParameters.size, 2)
        Assert.assertEquals(type.typeParameters[0].name, "kotlin.String")
        Assert.assertEquals(type.typeParameters[1].name, "kotlin.Double")
    }

    fun fullyQualified() {
        val qualified = KibbleType.from("java.math.BigDecimal")
        val decimal = KibbleType.from("BigDecimal")
        val integer = KibbleType.from("BigInteger")
        val dateTime = KibbleType.from("java.time.LocalDateTime")
        val list = KibbleType("List", "java.util", listOf(TypeParameter("String")))

        Assert.assertEquals(qualified.value, "BigDecimal")
        Assert.assertEquals(decimal.value, "BigDecimal")
        Assert.assertEquals(integer.value, "BigInteger")
        Assert.assertEquals(dateTime.value, "LocalDateTime")
        Assert.assertEquals(list.value, "java.util.List<String>")
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

    fun values() {
        val type = KibbleType("Class", "this.is.the.package", listOf(TypeParameter("K"), TypeParameter("V")), true,
                "Different", imported = true)

        Assert.assertEquals(type.fqcn, "this.is.the.package.Class")
        Assert.assertEquals(type.value, "Different<K, V>?")
    }
}