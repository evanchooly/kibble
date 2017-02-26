package com.antwerkz.kibble.model

import org.testng.Assert
import org.testng.annotations.Test

@Test
class KibbleTypeTest {
    fun simpleType() {
        val from = KibbleType.from("com.foo.bar.Type")

        Assert.assertEquals(from.qualifiedName, "com.foo.bar.Type")
        Assert.assertEquals(from.name, "Type")
        Assert.assertTrue(from.parameters.isEmpty())
    }

    fun generics() {
        val string = "com.foo.bar.Type<kotlin.String, kotlin.Double>?"
        val type = KibbleType.from(string)

        Assert.assertEquals(type.fullName, string)
        Assert.assertEquals(type.qualifiedName, "com.foo.bar.Type")
        Assert.assertEquals(type.name, "Type")
        Assert.assertTrue(type.nullable)
        Assert.assertEquals(type.parameters.size, 2)
        Assert.assertEquals(type.parameters[0].name, "String")
        Assert.assertEquals(type.parameters[0].qualifiedName, "kotlin.String")
        Assert.assertEquals(type.parameters[1].name, "Double")
        Assert.assertEquals(type.parameters[1].qualifiedName, "kotlin.Double")
    }
}