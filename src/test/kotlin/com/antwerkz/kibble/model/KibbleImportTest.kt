package com.antwerkz.kibble.model

import com.antwerkz.kibble.StringSourceWriter
import org.testng.Assert
import org.testng.annotations.Test

class KibbleImportTest {
    @Test
    fun imports() {
        var writer = StringSourceWriter()

        KibbleImport("com.foo.Bar").toSource(writer)
        Assert.assertEquals(writer.toString(), "import com.foo.Bar\n")

        writer = StringSourceWriter()
        KibbleImport("com.foo.Bar", "Harry").toSource(writer)
        Assert.assertEquals(writer.toString(), "import com.foo.Bar as Harry\n")
    }
}