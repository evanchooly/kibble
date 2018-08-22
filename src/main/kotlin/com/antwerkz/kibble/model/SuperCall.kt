package com.antwerkz.kibble.model

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock

class SuperCall {
    lateinit var type: ClassName
    val arguments: MutableList<CodeBlock> = mutableListOf()
}