package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble
import org.testng.annotations.Test

class TestKibbleObject {
    @Test
    fun companion() {
        val file = Kibble.parseFile("src/test/resources/com/antwerkz/test/KibbleObjectTest.kt")
        val klass = file.classes[0]

        println("klass = ${klass}")
    }
}