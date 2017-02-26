package com.antwerkz.kibble

import com.antwerkz.kibble.model.KibbleClass
import com.antwerkz.kibble.model.KibbleFile
import com.antwerkz.kibble.model.KibbleFunction
import com.antwerkz.kibble.model.KibbleProperty
import com.antwerkz.kibble.model.KibbleType
import com.antwerkz.kibble.model.Parameter
import com.antwerkz.kibble.model.Visibility
import org.jetbrains.kotlin.javax.inject.Singleton
import org.testng.Assert
import org.testng.annotations.Test
import java.io.File
import java.io.StringWriter


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

        Assert.assertEquals(file.imports.size, 4)
        Assert.assertEquals(file.imports[3].alias, "HMap")
        Assert.assertEquals(file.classes.size, 2)
        val klass = file.classes[0]

        Assert.assertTrue(klass.isInternal())
        Assert.assertTrue(klass.hasAnnotation(Singleton::class.java))
        Assert.assertEquals(klass.properties.size, 7, klass.properties.toString())
        Assert.assertEquals(klass.properties[0].name, "cost")
        Assert.assertEquals(klass.properties[0].type, KibbleType("Double"))
        Assert.assertEquals(klass.functions.size, 2)

        Assert.assertEquals(klass.functions[0].name, "output")
        Assert.assertEquals(klass.functions[0].parameters, listOf(Parameter("count", KibbleType("Long"))))

        Assert.assertEquals(klass.functions[1].name, "toString")
        Assert.assertEquals(klass.functions[1].parameters, listOf<Parameter>())
    }

    @Test
    fun writeSource() {
        val file = Kibble.parse(path)[0]

        val tempFile = File("kibble-test.kt")
        tempFile.deleteOnExit()
        FileSourceWriter(tempFile).use { file.toSource(it)}

        Assert.assertEquals(tempFile.readText().split("\n"), File(path).readLines())
    }

    @Test
    fun create() {
        val elements = KibbleClass(KibbleFile(), "KibbleTest")
                .markOpen()

        elements += KibbleProperty("property", KibbleType("Double"), parent = elements)
                .addInitializer("0.0")
        elements += KibbleFunction(KibbleFile(), "test", visibility = Visibility.PROTECTED,
                type = "Double",
                body = """println("hello")
return 0.0""")
        val file = KibbleFile(classes = mutableListOf(elements))

        val writer = StringWriter()
        StringSourceWriter(writer).use {
            file.toSource(it)
        }

        Assert.assertEquals(writer.toString(), File("src/test/resources/generated.kt").readText())
    }
}