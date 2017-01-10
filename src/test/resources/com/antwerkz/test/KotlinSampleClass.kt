package ktest

import java.util.ArrayList
import java.util.HashMap as HMap

internal open abstract class KotlinSampleClass(val cost: Double, ignored: Int) {
    protected open val age: Double = -1.0
    val list: List<String> = ArrayList()
    val map: HMap<String, Int> = HMap()
    var name: String? = null
    var time: Int? = null
    init {
        ignored + 1
    }

    constructor(name: String, time: Int) : this(2.0, 12) {
        this.name = name
        this.time = time
    }

    protected fun output(count: Long) {
        println("age = ${age}")
    }

    override fun toString(): String {
        return "KotlinSampleClass(name='$name', time=$time, age=$age, list=$list, map=$map)"
    }
}