package com.antwerkz.kibble

import com.antwerkz.kibble.model.Parameter
import org.testng.Assert
import org.testng.annotations.Test
import java.io.File

class KotlinFileListenerTest {
    @Test
    fun parse() {
        val file = Kibble.parse(File("src/test/resources/com/antwerkz/test/KotlinSampleClass.kt"))

        Assert.assertEquals(file.imports.size, 2)
        Assert.assertEquals(file.classes.size, 1)
        val klass = file.classes[0]

        Assert.assertEquals(klass.constructors.size, 1)
        Assert.assertEquals(klass.constructors[0].parameters, listOf(
                Parameter("name", "String"),
                Parameter("time", "Int")))
        Assert.assertEquals(klass.functions.size, 2)

        Assert.assertEquals(klass.functions[0].name, "output")
        Assert.assertEquals(klass.functions[0].parameters, listOf(Parameter("count", "Long")))

        Assert.assertEquals(klass.functions[1].name, "toString")
        Assert.assertEquals(klass.functions[1].parameters, listOf<Parameter>())
//        Assert.assertEquals(klass.functions[1].type, listOf<Parameter>())
//        Assert.assertEquals(klass.name, "KotlinSampleClass")
    }

    @Test
    fun parseFunctionSource() {
        val file = Kibble.parse(File("src/test/resources/com/antwerkz/test/standalone.kt"), true)

        Assert.assertEquals(file.functions.size, 1)
    }
}