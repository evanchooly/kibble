package com.antwerkz.kibble.model

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.TypeName

class SuperCall {
    lateinit var type: TypeName
    val arguments: MutableList<CodeBlock> = mutableListOf()
}