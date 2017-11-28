package com.antwerkz.kibble.model

import org.testng.Assert
import org.testng.annotations.Test

class KibbleFunctionTest {
    @Test
    fun varargs() {
        val file = KibbleFile()
        file.addFunction("temp", body = """return 4""")
                .addParameter("bob", "String", varargs = true)

        val source = file.toSource().toString()

        Assert.assertEquals(source, """
            fun temp(vararg bob: String) {
                return 4
            }
            """.trimIndent())
    }
}