package com.antwerkz.kibble.model

interface Polymorphic {
    fun extends(type: String, vararg arguments: String) {
        extends(KibbleType.from(type), *arguments)
    }

    fun extends(type: KibbleType, vararg arguments: String) {
        extends(type, arguments.map { KibbleArgument(it) })
    }

    fun extends(type: KibbleType, arguments: List<KibbleArgument>)

    fun implements(type: String) {
        implements(KibbleType.from(type))
    }

    fun implements(type: KibbleType)
}
