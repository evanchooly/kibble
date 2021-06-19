package com.antwerkz.kibble.model

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.TypeName

class SuperType {
    lateinit var type: TypeName
    var delegate: CodeBlock? = null
}
