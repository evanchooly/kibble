package com.antwerkz.kibble

import org.testng.Assert.assertNotNull
import org.testng.Assert.assertTrue
import org.testng.annotations.Test

class EnumTest {
    @Test
    fun enums() {
        val fileSpec = Kibble.parseSource(
                """
            enum class Timezones(val timezone: String) {
                Palmer("Antarctica/Palmer"),
                Rothera("Antarctica/Rothera"),
                Syowa("Antarctica/Syowa"),
                Mawson("Antarctica/Mawson"),
                Vostok("Antarctica/Vostok"),
                Davis("Antarctica/Davis"),
                Casey("Antarctica/Casey"),
                Dumont_D_Urville("Antarctica/DumontDUrville")
            }
        """.trimIndent())

        val typeSpec = fileSpec.classes[0]
        assertTrue(typeSpec.isEnum)
        assertNotNull(typeSpec.enumConstants["Davis"])
    }
}