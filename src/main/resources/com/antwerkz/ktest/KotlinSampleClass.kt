package com.antwerkz.ktest

import java.util.ArrayList
import java.util.HashMap

private class KotlinSampleClass(val name: String, var time: Int) {
    val age: Int = -1
    val list = ArrayList<String>()
    val map = HashMap<String, Int>()

    protected fun output(count: Long) {
        println("age = ${age}")
    }

    override fun toString(): String {
        return "KotlinSampleClass(name='$name', time=$time, age=$age, list=$list, map=$map)"
    }
}