package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble
import com.antwerkz.kibble.model.TypeParameterVariance.IN
import com.antwerkz.kibble.model.TypeParameterVariance.OUT
import org.intellij.lang.annotations.Language
import org.testng.Assert
import org.testng.annotations.Test

class KibbleClassTest {
    @Language("kotlin")
    private val source = """class Temp {
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
}""".trim()

    @Test
    fun nested() {
        var kibbleClass = Kibble.parseSource(source).classes[0]

        Assert.assertEquals(kibbleClass.classes.size, 1)
        Assert.assertEquals(kibbleClass.classes[0].name, "Nested")

        val file = KibbleFile()
        kibbleClass = file.addClass("Temp")
        val nested = kibbleClass.addClass("Nested")

        nested.extends("Foo", "\"bar\"")
        nested.implements(KibbleType.from("Interface"))

        nested.addSecondaryConstructor("blarg", "\"nargle\"")

        nested.initBlock = InitBlock("println()")
        nested.addProperty("val foo: Bob")
                .constructorParam = true
        nested.addProperty("val property: String")
        nested.addFunction("something", "Int", "return 4")

        val companion = kibbleClass.addCompanionObject()
        companion.addProperty("val prop = 42")
        kibbleClass.addObject("temp")

        Assert.assertEquals(kibbleClass.classes.size, 1)
        Assert.assertEquals(kibbleClass.classes[0].name, "Nested")

        Assert.assertTrue(kibbleClass.objects[0].companion)
        Assert.assertEquals(kibbleClass.objects[1].name, "temp")
        Assert.assertEquals(file.toSource().toString().trim(), source.trim())
    }

    @Test
    fun members() {
        val kibbleClass = Kibble.parseSource(source).classes[0]

        var obj = kibbleClass.companion()
        Assert.assertNotNull(obj, "Should find a companion object")
        Assert.assertNotNull(obj?.getProperty("prop"), "Should find a property named 'prop'")

        obj = kibbleClass.getObject("temp")
        Assert.assertNotNull(obj, "Should find an object named 'temp'")

        val kibble = kibbleClass.getClass("Nested") as KibbleClass
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
        Assert.assertNull(file.classes[0].extends)
//        Assert.assertTrue(file.classes[0].parentInterfaces.isEmpty())
        Assert.assertNull(file.classes[1].extends)
//        Assert.assertEquals(file.classes[1].parentInterfaces.size, 1)
//        Assert.assertEquals(file.classes[1].parentInterfaces[0], KibbleType("critter.test.source", "AbstractKotlinPerson"))
    }

    @Test
    fun noGenerics() {
        val source = """class NoGeneric {
}
"""
        val kibbleClass = Kibble.parseSource(source).classes[0]
        Assert.assertEquals(kibbleClass.toSource().toString(), source)
    }

    @Test
    fun generics() {
        val source = """class Generic<out T> {
}
"""
        val kibbleClass = Kibble.parseSource(source).classes[0]
        Assert.assertEquals(kibbleClass.toSource().toString(), source)

        val file = KibbleFile()
        var generic = file.addClass("Generic")
        generic.addTypeParameter(KibbleType(className = "T"), OUT)
        Assert.assertEquals(generic.toSource().toString(), source)

        generic = file.addClass("Generic")
        generic.addTypeParameter(KibbleType.from("T"), IN)
        Assert.assertEquals(generic.toSource().toString(), """class Generic<in T> {
}
""")

        generic = file.addClass("Generic")
        generic.addTypeParameter(KibbleType.from("K"))
        generic.addTypeParameter(KibbleType.from("V"))
        Assert.assertEquals(generic.toSource().toString(), """class Generic<K, V> {
}
""")

        generic = file.addClass("Generic")
        generic.addTypeParameter(KibbleType.from("K"), OUT)
        generic.addTypeParameter(KibbleType.from("V"), IN)
        Assert.assertEquals(generic.toSource().toString(), """class Generic<out K, in V> {
}
""")

        generic = file.addClass("Generic")
        generic.addTypeParameter(KibbleType.from("K"), OUT)
        generic.addTypeParameter(KibbleType.from("V"), IN)
        Assert.assertEquals(generic.toSource().toString(), """class Generic<out K, in V> {
}
""")
    }

    @Test
    fun nestedGenerics() {
        @Language("kotlin")
        val source = """class Generic<out T: Comparable<T>> {
}
"""
        val kibbleClass = Kibble.parseSource(source).classes[0]
        Assert.assertEquals(kibbleClass.toSource().toString().trim(), source.trim())
    }

    @Test
    fun abstractClasses() {
        @Language("kotlin")
        val source = """abstract class Abstract"""
        val kibbleClass = Kibble.parseSource(source).classes[0]
        Assert.assertTrue(kibbleClass.isAbstract())

    }

    @Test
    fun enumClasses() {
        @Language("kotlin")
        val source = """enum class Enum"""
        val kibbleClass = Kibble.parseSource(source).classes[0]
        Assert.assertTrue(kibbleClass.enum)
    }

    @Test
    fun implements() {
        val file = KibbleFile("temp.kt")
        file.addClass("Temp").also {
            it.implements("java.lang.Runnable")
        }

        val temp = """import java.lang.Runnable

class Temp: Runnable {
}"""
        Assert.assertEquals(file.toSource().toString().trim(), temp.trim())

        val parsed = Kibble.parseSource(temp)
        Assert.assertEquals(parsed.toSource().toString().trim(), temp.trim())
    }
}