package com.antwerkz.kibble.model

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

import typeName as aliasName
import java.lang.String as anotherAlias""", file.toSource().toString().trim())
    }

    @Test
    fun outputFile() {
        val file = KibbleFile("Foo.kt")
        Assert.assertEquals(file.outputFile(File("/tmp/")), File("/tmp/Foo.kt"))

        file.pkgName = "com.antwerkz.kibble"
        Assert.assertEquals(file.outputFile(File("/tmp/")), File("/tmp/com/antwerkz/kibble/Foo.kt"))
    }
}