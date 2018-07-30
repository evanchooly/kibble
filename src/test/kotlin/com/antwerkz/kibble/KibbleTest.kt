package com.antwerkz.kibble

import com.antwerkz.kibble.model.KibbleArgument
import com.antwerkz.kibble.model.KibbleFile
import com.antwerkz.kibble.model.KibbleParameter
import com.antwerkz.kibble.model.KibbleType
import com.antwerkz.kibble.model.Visibility
import org.testng.Assert
import org.testng.annotations.Test
import java.io.File
import javax.annotation.Generated

class KibbleTest {
    companion object {
        val path = "src/test/resources/com/antwerkz/test/KotlinSampleClass.kt"
    }

    @Test
    fun standalone() {
        val file = Kibble.parse("src/test/resources/com/antwerkz/test/standalone.kt")

        Assert.assertEquals(file.functions.size, 1)
        Assert.assertEquals(file.functions[0].visibility, Visibility.INTERNAL)
        Assert.assertEquals(file.functions[0].type, KibbleType(className = "String"))
        Assert.assertEquals(file.functions[0].body, """println("hi")
return "hi"""")
    }

    @Test
    fun sampleClass() {
        val file = Kibble.parse(path)

        Assert.assertEquals(file.imports.size, 3)
        Assert.assertEquals(file.classes.size, 3)
        val klass = file.classes[1]

        Assert.assertTrue(file.classes[0].isInterface)
        Assert.assertTrue(klass.isInternal())
        Assert.assertTrue(klass.hasAnnotation(Generated::class.java))
        Assert.assertEquals(klass.properties.size, 7, klass.properties.toString())
        Assert.assertEquals(klass.constructor.parameters.size, 1, klass.constructor.parameters.toString())

        listOf("cost", "name", "age", "list", "map", "time", "random").forEach {
            Assert.assertNotNull(klass.properties.firstOrNull { p -> p.name == it}, "Should find '$it: "
                    + klass.properties.map { p -> p.name})
        }
        listOf("output", "toString").forEach {
            Assert.assertNotNull(klass.functions.firstOrNull { f -> f.name == it}, "Should find '$it: "
                    + klass.functions.map { f -> f.name})
        }

        Assert.assertEquals(klass.functions.first { it.name == "output"}.parameters,
                listOf(KibbleParameter("count", KibbleType(className = "Long"))))

        Assert.assertEquals(klass.functions.first { it.name == "toString" }.parameters, listOf<KibbleParameter>())
    }

    @Test
    fun writeSource() {
        val file = Kibble.parse(path)

        val tempFile = File("kibble-test.kt")
        file.toSource().toFile(tempFile)

        Assert.assertEquals(tempFile.readText(), File(path).readText())
        tempFile.delete()
    }

    @Test
    fun create() {
        val file = KibbleFile("create.kt")
        val klass = file.addClass("KibbleTest")
                .markOpen()

        klass.addProperty("val property: Double = 0.0")
        klass.addFunction("test", type = "Double",
                body = """println("hello")
return 0.0""")
                .visibility = Visibility.PROTECTED

        file.addProperty("val topLevel: Int =4")

        file.addFunction("bareMethod", body = """println("hi")""")

        val generated = file.toSource().toString()

        Assert.assertEquals(generated, File("src/test/resources/generated.kt").readText())
    }

    @Test
    fun primaryConstructor() {
        val file = Kibble.parseSource("""
            class Simple(val num: Int)
        """.trimIndent())

        Assert.assertTrue(file.classes.size == 1)
        Assert.assertTrue(file.classes[0].constructor.parameters.size == 0)
        val properties = file.classes[0].properties
        Assert.assertTrue(properties.size == 1)
        Assert.assertEquals(properties[0].name, "num")
        Assert.assertEquals(properties[0].type.toString(), "Int")
    }
    @Test
    fun secondaryConstructors() {
        val file = Kibble.parseSource("""
            class Simple(val num: Int) {
                constructor(otherNum: Long): this(otherNum.toInt()) {
                    println("yo")
                }
            }
        """.trimIndent())

        val secondaryConstructor = file.classes[0].secondaries[0]
        val parameters = secondaryConstructor.parameters
        Assert.assertEquals(parameters[0].name, "otherNum")
        Assert.assertEquals(parameters[0].type.toString(), "Long")
        Assert.assertEquals(secondaryConstructor.delegationArguments, mutableListOf(KibbleArgument(value = "otherNum.toInt()")))
    }
}