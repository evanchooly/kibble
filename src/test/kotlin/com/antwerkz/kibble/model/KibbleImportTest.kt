package com.antwerkz.kibble.model

import com.antwerkz.kibble.StringSourceWriter
import org.testng.Assert
import org.testng.annotations.Test

class KibbleImportTest {
    @Test
    fun aliases() {
        Assert.assertEquals(
                KibbleImport("com.foo.Bar")
                        .toSource(StringSourceWriter())
                        .toString(),
                "import com.foo.Bar\n")


        Assert.assertEquals(
                KibbleImport("com.foo.Bar", "Harry")
                        .toSource(StringSourceWriter())
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
                        .toSource(StringSourceWriter())
                        .toString(),
                "import com.foo.Bar\n")
    }
}