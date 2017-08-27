package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble
import org.intellij.lang.annotations.Language
import org.testng.Assert
import org.testng.annotations.Test
import java.io.File

class KibbleFileTest {
    @Test(expectedExceptions = arrayOf(IllegalArgumentException::class))
    fun constructorProperties() {
        KibbleFile().addProperty("name", constructorParam = true)
    }

    @Test
    fun imports() {
        val file = KibbleFile()
        file.pkgName = "com.antwerkz.kibble"

        file.addImport("typeName", "aliasName")
        file.addImport(String::class.java, "anotherAlias")
        Assert.assertEquals("""package com.antwerkz.kibble

import java.lang.String as anotherAlias
import typeName as aliasName""", file.toSource().toString().trim())
    }

    @Test
    fun outputFile() {
        val file = KibbleFile("Foo.kt")
        Assert.assertEquals(file.outputFile(File("/tmp/")), File("/tmp/Foo.kt"))

        file.pkgName = "com.antwerkz.kibble"
        Assert.assertEquals(file.outputFile(File("/tmp/")), File("/tmp/com/antwerkz/kibble/Foo.kt"))
    }

/*
    @Test
    fun resolve() {
        @Language("kotlin")
        val source = """package com.antwerkz.testing

import com.foo.Bar
import com.zorg.Flur

class Main {
    val s: Second = Second()
    val t: Third = Third.HI
    val b: Bar = Bar()
    val f: com.zorg.Flur = com.zorg.Flur()
    val g: Generic<Int>
}

class Second""".trim()

        @Language("kotlin")
        val source2 = """package com.antwerkz.testing

enum class Third {
    HI
}

class Generic<T>"""
        val file = Kibble.parseSource(source)
        Kibble.parseSource(source2, file.context)
        val props = file.classes[0].properties.iterator()

        check(file, props.next(), "Second", "com.antwerkz.testing.Second")
        check(file, props.next(), "Third", "com.antwerkz.testing.Third")
        check(file, props.next(), "Bar", "com.foo.Bar")
        check(file, props.next(), "com.zorg.Flur", "com.zorg.Flur")
        check(file, props.next(), "Generic<Int>", "com.antwerkz.testing.Generic<Int>")

    }

    private fun check(file: KibbleFile, property: KibbleProperty, expectedName: String, expectedResolved: String) {
        val type = property.type!!
        Assert.assertEquals(type.value, expectedName)
        Assert.assertEquals(file.resolve(type).value, expectedResolved)
    }
*/

    @Test
    fun normalize() {
        val file = KibbleFile("test.kt")
        val list = file.normalize(KibbleType.from("java.util.List"))
        val set = file.normalize(KibbleType.from("java.util.Set"))
        val list2 = file.normalize(KibbleType.from("java.util.List"))
        val plainList = file.normalize(KibbleType.from("List"))

        Assert.assertEquals(list.value, "List")
        Assert.assertEquals(list2.value, "List")
        Assert.assertEquals(plainList.value, "List")
        Assert.assertEquals(set.value, "Set")

        file.addImport(java.awt.List::class.java, "awtList")
        val awt = file.normalize(KibbleType.from("java.awt.List"))
        Assert.assertEquals(awt.value, "awtList")
    }

}