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
        Assert.assertTrue(type?.parameters?.isEmpty() ?: false)
    }

    fun generics() {
        val string = "com.foo.bar.SomeType<kotlin.String, kotlin.Double>?"
        val file = Kibble.parseSource("val foo: $string")
        val type = file.properties[0].type

        Assert.assertEquals(type?.fullName, string)
        Assert.assertEquals(type?.name, "com.foo.bar.SomeType")
        Assert.assertTrue(type?.nullable ?: false)
        Assert.assertEquals(type?.parameters?.size, 2)
        Assert.assertEquals(type?.parameters?.get(0)?.name, "kotlin.String")
        Assert.assertEquals(type?.parameters?.get(1)?.name, "kotlin.Double")
    }

    fun fullyQualified() {
        val qualified = KibbleType.from("java.math.BigDecimal")
        val decimal = KibbleType.from("BigDecimal")
        val integer = KibbleType.from("BigInteger")
        val dateTime = KibbleType.from("java.time.LocalDateTime")

        Assert.assertEquals(qualified.fullName, "java.math.BigDecimal")
        Assert.assertEquals(decimal.fullName, "BigDecimal")
        Assert.assertEquals(integer.fullName, "BigInteger")
        Assert.assertEquals(dateTime.name, "java.time.LocalDateTime")
        Assert.assertEquals(dateTime.fullName, "java.time.LocalDateTime")
    }
}