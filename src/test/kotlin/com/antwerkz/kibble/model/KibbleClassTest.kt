package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble
import com.antwerkz.kibble.classes
import com.antwerkz.kibble.companion
import com.antwerkz.kibble.getClass
import com.antwerkz.kibble.getFunctions
import com.antwerkz.kibble.getObject
import com.antwerkz.kibble.getProperty
import com.antwerkz.kibble.isAbstract
import com.squareup.kotlinpoet.ANY
import com.squareup.kotlinpoet.ClassName
import org.intellij.lang.annotations.Language
import org.testng.Assert
import org.testng.annotations.Test

class KibbleClassTest {
    @Language("kotlin")
    private val source = """
        class Temp {
            companion object {
                val prop = 42
            }

            object temp

            class Nested(val foo: Bob): Foo("bar"), Interface {
                constructor(): this(blarg, "nargle")

                init {
                    println()
                }

                val property: String

                fun something(): Int {
                    return 4
                }
            }
        }

        """.trimIndent()

    @Test
    fun members() {
        val kibbleClass = Kibble.parseSource(source).classes.first()

        var obj = kibbleClass.companion()
        Assert.assertNotNull(obj, "Should find a companion object")
        Assert.assertNotNull(obj?.getProperty("prop"), "Should find a property named 'prop'")

        obj = kibbleClass.getObject("temp")
        Assert.assertNotNull(obj, "Should find an object named 'temp'")

        val kibble = kibbleClass.getClass("Nested")!!
        Assert.assertNotNull(kibble.getProperty("property"), "Should find a property named 'property'")
        Assert.assertEquals(kibble.getFunctions("something").size, 1, "Should find one function named 'something'")
    }

    @Test
    fun parent() {
        val file = Kibble.parseSource("""
package critter.test.source

import org.bson.types.ObjectId
import org.mongodb.morphia.annotations.Entity
import org.mongodb.morphia.annotations.Id

open class AbstractKotlinPerson {
    var age: Long? = null
}

@Entity
open class Person : AbstractKotlinPerson {
    @Id
    var id: ObjectId? = null

    var first: String? = null

    var last: String? = null
} """)
        val classes = file.classes.iterator()
        var clazz = classes.next()
        Assert.assertEquals(clazz.superclass, ANY)
        Assert.assertTrue(clazz.superinterfaces.isEmpty())
        clazz = classes.next()
        Assert.assertEquals(clazz.superclass, ANY)
        Assert.assertNotNull(clazz.superinterfaces.containsKey(ClassName("", "AbstractKotlinPerson")))
    }

    @Test
    fun abstractClasses() {
        @Language("kotlin")
        val source = """abstract class Abstract"""
        val kibbleClass = Kibble.parseSource(source).classes.first()
        Assert.assertTrue(kibbleClass.isAbstract())

    }

    @Test
    fun enumClasses() {
        @Language("kotlin")
        val source = """enum class Enum {
    ENUM1
}""".trimMargin()
        val kibbleClass = Kibble.parseSource(source).classes.first()
        Assert.assertTrue(kibbleClass.isEnum)
    }
}