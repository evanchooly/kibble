package com.antwerkz.kibble

import com.squareup.kotlinpoet.KModifier.OVERRIDE
import org.testng.Assert
import org.testng.annotations.Test

class OverridableTest {
    @Test
    fun overridable() {
        val file = Kibble.parseSource(
            """
    override fun temp() {
    }
"""
        )
        Assert.assertTrue(file.functions.first().modifiers.contains(OVERRIDE))
    }
}
