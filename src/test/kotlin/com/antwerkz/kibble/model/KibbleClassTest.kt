package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble
import org.testng.Assert
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
}