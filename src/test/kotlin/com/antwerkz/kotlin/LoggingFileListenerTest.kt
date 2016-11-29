package com.antwerkz.kotlin

import org.testng.Assert
import org.testng.Assert.*
import org.testng.annotations.Test
import kotlin.reflect.declaredMemberFunctions

class LoggingFileListenerTest {
    @Test
    fun validateOverrides() {
        val functions = KotlinParserBaseListener::class.declaredMemberFunctions
                .filter { it.name.startsWith("enter") || it.name.startsWith("exit")}
                .filterNot { it.name.endsWith("EveryRule") }
                .map { it.name }
                .sortedBy { it }
        val overrides = LoggingFileListener::class.declaredMemberFunctions
                .filter { it.name.startsWith("enter") || it.name.startsWith("exit")}
                .map { it.name }
                .sortedBy { it }

        Assert.assertEquals(overrides, functions)
    }
}