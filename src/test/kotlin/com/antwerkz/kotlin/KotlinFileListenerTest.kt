package com.antwerkz.kotlin

import com.antwerkz.kotlin.model.Parameter
import org.testng.Assert
import org.testng.annotations.Test

class KotlinFileListenerTest {
    @Test
    fun parse() {
        val files = Klara.parse("src/main/resources/com/antwerkz/ktest/KotlinSampleClass.kt")

        Assert.assertEquals(files.size, 1)
        val file = files[0]

        Assert.assertEquals(file.imports.size, 2)
        Assert.assertEquals(file.classes.size, 1)
        val klass = file.classes[0]

        println("hi")
        Assert.assertEquals(klass.constructors.size, 1)
        Assert.assertEquals(klass.constructors[0].parameters, listOf(Parameter("val", "name", "String"), Parameter("var", "time", "Int")))
        Assert.assertEquals(klass.functions.size, 2)
//        Assert.assertEquals(klass.functions[0].name, "output")
        Assert.assertEquals(klass.functions[0].parameters, listOf<Parameter>())
        Assert.assertEquals(klass.functions[1].parameters, listOf<Parameter>())
//        Assert.assertEquals(klass.functions[1].type, listOf<Parameter>())
//        Assert.assertEquals(klass.name, "KotlinSampleClass")
//        Assert.assertEquals(klass.functions.size, 2)
    }
}