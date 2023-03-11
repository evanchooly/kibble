package com.antwerkz.kibble

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.KModifier.INTERNAL
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeSpec.Kind.INTERFACE
import kotlin.test.assertEquals
import org.testng.Assert
import org.testng.annotations.Test

class KibbleTest {
    companion object {
        const val path = "src/test/resources/com/antwerkz/test/KotlinSampleClass.kt"
    }

    @Test
    fun standalone() {
        val file = Kibble.parse("src/test/resources/com/antwerkz/test/standalone.kt")

        Assert.assertEquals(file.functions.size, 1)
        val first = file.functions.first()
        Assert.assertEquals(first.visibility, KModifier.INTERNAL)
        Assert.assertEquals(first.returnType.toString(), "kotlin.String")
        Assert.assertEquals(first.body, CodeBlock.of("""println("hi")
return "hi""""))
    }

    @Test
    fun sampleClass() {
        val file = Kibble.parse(path)
        val next = file.interfaces.first()
        Assert.assertTrue(next.kind == INTERFACE)
        val klass = file.getClass("KotlinSampleClass")!!
        Assert.assertTrue(klass.visibility == INTERNAL)
        val generated =
            klass.annotationSpecs.firstOrNull {
                it.typeName == ClassName("javax.annotation", "Generated")
            }
        Assert.assertTrue(generated != null)
        generated?.let {
            val arguments = generated.arguments()
            assertEquals(arguments[""], "\"I'm the value\"")
            assertEquals(arguments["date"], "\"123455\"")
            assertEquals(arguments["comments"], "\"Fingers crossed\"")
            assertEquals(arguments["number"], "49152")
        }
        Assert.assertEquals(klass.propertySpecs.size, 7, klass.propertySpecs.toString())
        Assert.assertEquals(
            klass.primaryConstructor?.parameters?.size,
            2,
            klass.primaryConstructor?.parameters.toString()
        )

        listOf("cost", "name", "age", "list", "map", "time", "random").forEach {
            Assert.assertNotNull(
                klass.propertySpecs.firstOrNull { p -> p.name == it },
                "Should find '$it: " + klass.propertySpecs.map { p -> p.name }
            )
        }
        listOf("output", "toString").forEach {
            Assert.assertNotNull(
                klass.funSpecs.firstOrNull { f -> f.name == it },
                "Should find '$it: " + klass.funSpecs.map { f -> f.name }
            )
        }

        Assert.assertEquals(
            klass.funSpecs.first { it.name == "output" }.parameters,
            listOf(ParameterSpec.builder("count", ClassName("kotlin", "Long")).build())
        )

        Assert.assertTrue(klass.funSpecs.first { it.name == "toString" }.parameters.isEmpty())
    }

    @Test
    fun primaryConstructor() {
        val file =
            Kibble.parseSource(
                """
            class Simple(val num: Int)
            """
                    .trimIndent()
            )

        Assert.assertTrue(file.classes.size == 1)
        Assert.assertEquals(
            file.classes.first().primaryConstructor?.parameters?.size,
            1,
            file.classes.first().primaryConstructor?.parameters.toString()
        )
        val properties = file.classes.first().propertySpecs
        Assert.assertTrue(properties.size == 1)
        Assert.assertEquals(properties[0].name, "num")
    }

    @Test
    fun secondaryConstructors() {
        val file =
            Kibble.parseSource(
                """
            class Simple(val num: Int) {
                constructor(otherNum: Long): this(otherNum.toInt()) {
                    this.num = num
                }
            }
            """
                    .trimIndent()
            )
        val secondaryConstructor = file.classes.first().secondaries[0]
        val parameters = secondaryConstructor.parameters
        Assert.assertEquals(parameters[0].name, "otherNum")
        Assert.assertEquals(
            secondaryConstructor.delegateConstructorArguments,
            mutableListOf(CodeBlock.of("otherNum.toInt()"))
        )
        Assert.assertEquals(secondaryConstructor.body, CodeBlock.of("this.num = num"))
    }
}
