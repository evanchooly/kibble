package com.antwerkz.kibble.model

import com.squareup.kotlinpoet.FunSpec

data class Accessor(val function: FunSpec)
data class Mutator(val function: FunSpec)
