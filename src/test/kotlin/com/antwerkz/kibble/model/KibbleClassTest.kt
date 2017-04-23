package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble
import org.intellij.lang.annotations.Language
import org.testng.Assert
import org.testng.annotations.Test

class KibbleClassTest {
    @Test
    fun nested() {
        @Language("kotlin")
        val source = """class Temp {
    object temp
    class Nested(val foo: Bob) : Foo("bar"), Interface {
        constructor() : this(blarg, "nargle")

        val property: String

        fun something(): Int {
            return 4
        }
    }
}""".trim()
        val parsed = Kibble.parseSource(source)
        var kibbleClass = parsed.classes[0]

        Assert.assertEquals(kibbleClass.classes.size, 1)
        Assert.assertEquals(kibbleClass.classes[0].name, "Nested")

        val kibbleFile = KibbleFile()
        kibbleClass = kibbleFile.addClass("Temp")
        val nested = kibbleClass.addClass("Nested")

        nested.superType = KibbleType.from("Foo")
        nested.superCallArgs = listOf("\"bar\"")
        nested.superTypes += KibbleType.from("Interface")

        nested.addSecondaryConstructor().delegationArguments += listOf("blarg", "\"nargle\"")

        nested.addProperty("foo", "Bob", constructorParam = true)
        nested.addProperty("property", "String")
        nested.addFunction("something", "Int", "return 4")
        val obj = kibbleClass.addObject("temp")

        Assert.assertEquals(kibbleClass.classes.size, 1)
        Assert.assertEquals(kibbleClass.classes[0].name, "Nested")

        Assert.assertEquals(kibbleClass.objects[0].name, "temp")
        Assert.assertEquals(kibbleFile.toSource().toString().trim(), source.trim())
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
        Assert.assertNull(file.classes[0].superType)
        Assert.assertTrue(file.classes[0].superTypes.isEmpty())
        Assert.assertNull(file.classes[1].superType)
        Assert.assertEquals(file.classes[1].superTypes.size, 1)
        Assert.assertEquals(file.classes[1].superTypes[0], KibbleType(name = "AbstractKotlinPerson"))
    }
}