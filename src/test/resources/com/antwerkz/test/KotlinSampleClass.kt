package com.antwerkz.test

import java.util.ArrayList
import java.util.HashMap as HMap

internal abstract class KotlinSampleClass(val cost: Double, ignored: Int) {
    protected open val age: Double = -1.0
    val list: List<String> = ArrayList()
    val map: java.util.HashMap<String, Int> = java.util.HashMap()
    var name: String? = null
    var time: Int? = null
    lateinit var random: String

    protected fun output(count: Long) {
        println("age = $age")
    }

    override fun toString(): String {
        return "KotlinSampleClass(name='$name', time=$time, age=$age, list=$list, map=$map)"
    }
}
