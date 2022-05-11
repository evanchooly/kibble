package com.antwerkz.kibble

import org.testng.Assert
import org.testng.annotations.Test

class Constructors {
    @Test
    fun constructors() {
        val file = Kibble.parseSource(
            """
            class Item(var name: String, var price: Double) {
                constructor() : this("", 0.0)
            }
            """.trimIndent()
        )
        val primary = file.classes[0].primaryConstructor
        Assert.assertEquals(primary?.parameters?.size, 2)
        val secondary = file.classes[0].secondaries[0]
        Assert.assertEquals(secondary.parameters.size, 0)
    }
}
