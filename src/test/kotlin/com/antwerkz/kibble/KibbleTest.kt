package com.antwerkz.kibble

import com.antwerkz.kibble.model.KotlinClass
import com.antwerkz.kibble.model.KotlinFile
import com.antwerkz.kibble.model.KotlinFunction
import com.antwerkz.kibble.model.KotlinProperty
import com.antwerkz.kibble.model.KotlinType
import com.antwerkz.kibble.model.Parameter
import com.antwerkz.kibble.model.Visibility
import org.testng.Assert
import org.testng.annotations.Test
import java.io.File


class KibbleTest {
    companion object {
        val path = "src/test/resources/com/antwerkz/test/KotlinSampleClass.kt"
    }

    @Test
    fun standalone() {
        val file = Kibble.parse("src/test/resources/com/antwerkz/test/standalone.kt")[0]

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
        val file = Kibble.parse(path)[0]

        Assert.assertEquals(file.imports.size, 2)
        Assert.assertEquals(file.imports[1].alias, "HMap")
        Assert.assertEquals(file.classes.size, 2)
        val klass = file.classes[0]

        Assert.assertTrue(klass.isInternal())
        Assert.assertEquals(klass.properties.size, 7, klass.properties.toString())
        Assert.assertEquals(klass.properties[0].name, "cost")
        Assert.assertEquals(klass.properties[0].type, KotlinType("Double", nullable = false))
        Assert.assertEquals(klass.functions.size, 2)

        Assert.assertEquals(klass.functions[0].name, "output")
        Assert.assertEquals(klass.functions[0].parameters, listOf(Parameter("count", KotlinType("Long", nullable = false))))

        Assert.assertEquals(klass.functions[1].name, "toString")
        Assert.assertEquals(klass.functions[1].parameters, listOf<Parameter>())
    }

    @Test
    fun writeSource() {
        val file = Kibble.parse(path)[0]

        val tempFile = File("kibble-test.kt")
//        tempFile.deleteOnExit()
        FileSourceWriter(tempFile).use { file.toSource(it)}

        val readLines = tempFile.readLines()
        Assert.assertEquals(readLines, File(path).readLines())
    }

    @Test
    fun create() {
        val elements = KotlinClass("KibbleTest")

        elements += KotlinProperty("property", KotlinType("Double", nullable = false), lateInit = false)
        elements += KotlinFunction("test", visibility = Visibility.PROTECTED,
                type = "Double",
                body = """println("hello")""")
        val file = KotlinFile(classes = mutableListOf(elements))

        ConsoleSourceWriter().use {
            file.toSource(it)
        }
    }
}