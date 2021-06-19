package com.antwerkz.kibble.model

import com.squareup.kotlinpoet.ClassName

data class ImportDirective(val className: ClassName, val packageName: String, val name: String, val alias: String?)
