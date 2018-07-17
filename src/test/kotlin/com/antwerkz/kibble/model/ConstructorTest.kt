package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble
import org.testng.Assert
import org.testng.annotations.Test

class ConstructorTest {
    @Test
    fun constructors() {
        val classes = Kibble.parseSource("""
class Factory(val type: String) {
    constructor(): this("car")
}
        """).classes.iterator()

        val klass = classes.next()
        Assert.assertTrue(klass.constructor.parameters.isEmpty())
        Assert.assertEquals("type", klass.properties[0].name)

        val secondaries = klass.secondaries.iterator()
        val secondary = secondaries.next()
        Assert.assertNotNull(secondary)
        Assert.assertTrue(secondary.parameters.isEmpty())
    }

    @Test
    fun imports() {
        val file = KibbleFile()
        val temp = file.addClass("temp")
        temp.addProperty("val temp: com.foo.Bob")
                .constructorParam = true
        temp.addSecondaryConstructor()
                .addParameter("foo", "org.box.Bla")

        val source = file.toSource().toString()
        //language=kotlin
        Assert.assertEquals(source, """import com.foo.Bob
import org.box.Bla

class temp(val temp: Bob) {
    constructor(foo: Bla): this()

}

            """.trimIndent())
    }
}
