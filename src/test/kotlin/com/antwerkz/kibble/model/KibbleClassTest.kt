package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble
import org.testng.Assert
import org.testng.Assert.fail
import org.testng.annotations.Test

class KibbleClassTest {
    @Test
    fun nested() {
        val source = """
class Temp {
    class Nested {
        val property: String

        fun something(): Int {
            return 4
        }
    }
}""".trim()
        var kibbleClass = Kibble.parseSource(source).classes[0]

        Assert.assertEquals(kibbleClass.nestedClasses.size, 1)
        Assert.assertEquals(kibbleClass.nestedClasses[0].name, "Nested")
        Assert.assertEquals(kibbleClass.toSource().toString().trim(), source.trim())

        kibbleClass = KibbleFile()
                .addClass("Temp")
        val nested = kibbleClass.addClass("Nested")
        nested.addProperty("property", "String")
        nested.addFunction("something", "Int", "return 4")

        Assert.assertEquals(kibbleClass.nestedClasses.size, 1)
        Assert.assertEquals(kibbleClass.nestedClasses[0].name, "Nested")
        Assert.assertEquals(kibbleClass.toSource().toString().trim(), source.trim())
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
        Assert.assertEquals(file.classes[1].superTypes[0], KibbleType("critter.test.source.AbstractKotlinPerson", "AbstractKotlinPerson"))
    }
}