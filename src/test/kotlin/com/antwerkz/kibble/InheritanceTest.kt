package com.antwerkz.kibble

import org.testng.Assert
import org.testng.annotations.Test

class InheritanceTest {
    @Test
    fun parentClass() {
        val types = Kibble.parseSource(
            """
            abstract class Parent {
            }
            
            class Child: Parent() {
            }
            """.trimIndent()
        ).classes
        val child = types.first { it.name == "Child" }

        Assert.assertEquals(child.superclass.toString(), "Parent")
    }
}
