package com.antwerkz.kibble

import com.antwerkz.kibble.model.Parameter
import com.antwerkz.kibble.model.Visibility
import org.testng.Assert
import org.testng.annotations.Test
import java.io.File

class KibbleTest {
    @Test
    fun standalone() {
        val file = Kibble.compile("src/test/resources/com/antwerkz/test/standalone.kt")[0]

        Assert.assertEquals(file.functions.size, 1)
        Assert.assertEquals(file.functions[0].visibility, Visibility.INTERNAL)
        Assert.assertEquals(file.functions[0].type, "String")
        Assert.assertEquals(file.functions[0].body, """{
    println("hi")
    return "hi"
}""")
    }

    @Test
    fun sampleClass() {
        val file = Kibble.compile("src/test/resources/com/antwerkz/test/KotlinSampleClass.kt")[0]

        Assert.assertEquals(file.imports.size, 2)
        Assert.assertEquals(file.imports[1].alias, "HMap")
        Assert.assertEquals(file.classes.size, 1)
        val klass = file.classes[0]

        Assert.assertTrue(klass.isInternal())
        Assert.assertEquals(klass.properties.size, 6)
        Assert.assertEquals(klass.properties[0].name, "cost")
        Assert.assertEquals(klass.properties[0].type, "Double")
        Assert.assertEquals(klass.secondaries.size, 1)
        Assert.assertEquals(klass.secondaries[0].parameters, listOf(
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

}