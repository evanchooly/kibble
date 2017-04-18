package com.antwerkz.kibble.model

import org.testng.Assert
import org.testng.annotations.Test

class KibbleImportTest {
    @Test
    fun aliases() {
        Assert.assertEquals(
                KibbleImport(KibbleType("com.foo.Bar", "Bar"))
                        .toSource()
                        .toString(),
                "import com.foo.Bar\n")


        Assert.assertEquals(
                KibbleImport(KibbleType("com.foo.Bar", "Bar"), "Harry")
                        .toSource()
                        .toString(),
                "import com.foo.Bar as Harry\n")
    }

    @Test
    fun duplicates() {
        val file = KibbleFile()
        file.addImport("com.foo.Bar")
        file.addImport("com.foo.Bar")


        Assert.assertEquals(
                file
                        .toSource()
                        .toString(),
                "import com.foo.Bar\n\n")
    }
}