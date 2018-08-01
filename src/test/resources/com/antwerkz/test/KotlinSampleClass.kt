package com.antwerkz.test

import java.util.ArrayList
import java.util.HashMap as HMap
import javax.annotation.Generated

val something: String = ""

interface Dummy {
    fun what()
}

@Generated("I'm the value", date = "123455", comments = "Fingers crossed")
internal abstract class KotlinSampleClass(val cost: Double, ignored: Int): ParentClass(21), Cloneable {
    var name: String? = null

    protected open val age: Double = -1.0

    val list: List<String> = ArrayList()

    val map: HMap<String, Int> = java.util.HashMap()

    var time: Int? = null

    @SuppressWarnings("message")
    protected lateinit var random: String

    @SuppressWarnings("message")
    protected fun output(count: Long) {
        println("age = $age")
    }

    override fun toString(): String {
        return "KotlinSampleClass(name='$name', time=$time, age=$age, list=$list, map=$map)"
    }
}

open class ParentClass(val blurb: Int) {
}
