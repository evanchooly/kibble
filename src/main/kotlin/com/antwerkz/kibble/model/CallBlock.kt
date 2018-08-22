package com.antwerkz.kibble.model

import com.squareup.kotlinpoet.CodeBlock

sealed class CallBlock {
    val arguments = mutableListOf<CodeBlock>()

    class ThisCallBlock : CallBlock()
    class SuperCallBlock : CallBlock()
}