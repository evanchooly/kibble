package com.antwerkz.kibble

import com.antwerkz.kibble.model.KibbleFile
import com.antwerkz.kibble.model.KibbleParameter
import com.antwerkz.kibble.model.KibbleType
import com.antwerkz.kibble.model.Visibility
import org.jetbrains.kotlin.javax.inject.Singleton
import org.testng.Assert
import org.testng.annotations.Test
import java.io.File

class KibbleTest {
    companion object {
        val path = "src/test/resources/com/antwerkz/test/KotlinSampleClass.kt"
    }

    @Test
    fun standalone() {
        val file = Kibble.parseFile("src/test/resources/com/antwerkz/test/standalone.kt")

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
        val file = Kibble.parseFile(path)

        Assert.assertEquals(file.imports.size, 4)
        Assert.assertEquals(file.classes.size, 2)
        val klass = file.classes[0]

        Assert.assertTrue(klass.isInternal())
        Assert.assertTrue(klass.hasAnnotation(Singleton::class.java))
        Assert.assertEquals(klass.properties.size, 7, klass.properties.toString())
        Assert.assertEquals(klass.constructor.parameters.size, 2, klass.constructor.parameters.toString())
        Assert.assertEquals(klass.properties[0].name, "cost")
        Assert.assertEquals(klass.properties[0].type, KibbleType("Double"))
        Assert.assertEquals(klass.functions.size, 2)

        Assert.assertEquals(klass.functions[0].name, "output")
        Assert.assertEquals(klass.functions[0].parameters, listOf(KibbleParameter("count", KibbleType("Long"))))

        Assert.assertEquals(klass.functions[1].name, "toString")
        Assert.assertEquals(klass.functions[1].parameters, listOf<KibbleParameter>())
    }

    @Test
    fun writeSource() {
        val file = Kibble.parseFile(path)

        val tempFile = File("kibble-test.kt")
        try {
            FileSourceWriter(tempFile).use { file.toSource(it) }

            Assert.assertEquals(tempFile.readText().trim().split("\n"), File(path).readText().trim().split("\n"))
            tempFile.delete()
        } finally {}
    }

    @Test
    fun create() {
        val file = KibbleFile("create.kt")
        val klass = file.addClass("KibbleTest")
                .markOpen()

        klass.addProperty("property", "Double", initializer = "0.0")
        klass.addFunction("test", type = "Double",
                body = """println("hello")
return 0.0""")
                .visibility = Visibility.PROTECTED

        file.addProperty("topLevel", "Int")
                .initializer = "4"

        file.addFunction("bareMethod", body= """println("hi")""")

        val generated = StringSourceWriter().use { file.toSource(it).toString() }

        Assert.assertEquals(generated, File("src/test/resources/generated.kt").readText())
    }
}