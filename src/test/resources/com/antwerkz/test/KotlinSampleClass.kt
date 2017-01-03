package ktest

import java.util.ArrayList
import java.util.HashMap

internal open class KotlinSampleClass(val name: String, var time: Int) {
    protected open val age: Double = -1.0
    val list: List<String> = ArrayList()
    val map = HashMap<String, Int>()

    protected fun output(count: Long) {
        println("age = ${age}")
    }

    override fun toString(): String {
        return "KotlinSampleClass(name='$name', time=$time, age=$age, list=$list, map=$map)"
    }
}