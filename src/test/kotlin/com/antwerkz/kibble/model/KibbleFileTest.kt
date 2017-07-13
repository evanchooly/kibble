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

    @Test
    fun resolve() {
        @Language("kotlin")
        val source = """package com.antwerkz.testing

import com.foo.Bar
import com.zorg.Flur

class Main {
    val s: Second = Second()
    val t: Third = HI
    val b: Bar = Bar()
    val f: com.zorg.Flur = com.zorg.Flur()
}

class Second

enum class Third {
    HI
}""".trim()

        val file = Kibble.parseSource(source)
        val props = file.classes[0].properties.iterator()

        check(file, props.next(), "Second", "com.antwerkz.testing.Second")
        check(file, props.next(), "Third", "com.antwerkz.testing.Third")
        check(file, props.next(), "Bar", "com.foo.Bar")
        check(file, props.next(), "com.zorg.Flur", "com.zorg.Flur")

    }

    private fun check(file: KibbleFile, f: KibbleProperty, name: String, resolvedName: String) {
        Assert.assertEquals(name, f.type?.name)
        Assert.assertEquals(resolvedName, file.resolve(f.type!!).name)
    }
}