package com.antwerkz.kibble

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.TypeSpec.Companion.classBuilder
import org.testng.Assert
import org.testng.annotations.Test
import com.squareup.kotlinpoet.FileSpec.Companion.builder as file
import com.squareup.kotlinpoet.FunSpec.Companion.constructorBuilder as ctor
import com.squareup.kotlinpoet.ParameterSpec.Companion.builder as parameter
import com.squareup.kotlinpoet.PropertySpec.Companion.builder as property

class ConstructorTest {
    @Test
    fun secondaries() {
        val classes = Kibble.parseSource("""
class Factory(val type: String) {
    constructor(): this("car")
}
        """).classes.iterator()

        val klass = classes.next()
        Assert.assertEquals(klass.primaryConstructor?.parameters?.size, 1)
        Assert.assertEquals("type", klass.propertySpecs[0].name)

        val secondaries = klass.secondaries.iterator()
        val secondary = secondaries.next()
        Assert.assertNotNull(secondary)
        Assert.assertTrue(secondary.parameters.isEmpty())
    }
    @Test
    fun constructors() {
        val fileSpec = Kibble.parseSource(
                """
class Factory(val type: String = "red")
        """
        )
        val classes = fileSpec.classes.iterator()

        val klass = classes.next()
        val primaryConstructor = klass.primaryConstructor!!
        Assert.assertEquals(primaryConstructor.parameters.size, 1)
        Assert.assertEquals("type", primaryConstructor.parameters[0].name)
        Assert.assertEquals(CodeBlock.of("\"red\""), primaryConstructor.parameters[0].defaultValue)
        Assert.assertEquals("type", klass.propertySpecs[0].name)
    }

    @Test
    fun props() {
        val string = ClassName("", "String")
     file("", "temp").addType(
             classBuilder("temp").primaryConstructor(
                     ctor().addParameter(parameter("arg", string)
                                                 .defaultValue(""""hello world"""")
                                                 .build()).build()
             ).addProperty(
                     property("arg", string)
                             .initializer("arg")
     //                                .initializer(""""hello world"""")
                             .build()
             ).build()
     ).build().writeTo(System.out)

    }
}
