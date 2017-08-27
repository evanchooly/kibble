package com.antwerkz.kibble.model

import org.testng.Assert
import org.testng.annotations.Test

class KibbleImportTest {
    @Test
    fun aliases() {
        Assert.assertEquals(
                KibbleImport(KibbleType("com.foo.Bar"))
                        .toSource()
                        .toString(),
                "import com.foo.Bar\n")


        Assert.assertEquals(
                KibbleImport(KibbleType("com.foo.Bar", "Harry"))
                        .toSource()
                        .toString(),
                "import com.foo.Bar as Harry\n")
    }


    @Test
    fun duplicateImport() {
        val file = KibbleFile("Foo.kt")
        tryImport(file, "com.foo.Bar")
        tryImport(file, "com.foo.Bar", "Second")
        tryImport(file, String::class.java)
        tryImport(file, String::class.java, "Second")
    }

    private fun tryImport(file: KibbleFile, type: String, alias: String? = null) {
        Assert.assertNotNull(file.addImport(type, alias))
        Assert.assertNull(file.addImport(type, alias))
        alias?.let {
            Assert.assertNull(file.addImport(type, alias))
        }
    }

    private fun tryImport(file: KibbleFile, type: Class<*>, alias: String? = null) {
        Assert.assertNotNull(file.addImport(type, alias))
        Assert.assertNull(file.addImport(type, alias))
        alias?.let {
            Assert.assertNull(file.addImport(type, alias))
        }
    }
}