package com.antwerkz.kibble.model

interface Polymorphic {
    fun addSuperType(type: KibbleType)

    fun addSuperCallArg(argument: KibbleArgument)

    fun addSuperCallArg(argument: String) {
        addSuperCallArg(KibbleArgument(argument))
    }

    fun extends(type: String, vararg arguments: String) {
        extends(KibbleType.from(type), *arguments)
    }

    fun extends(type: KibbleType, vararg arguments: String)

    fun implements(type: String) {
        implements(KibbleType.from(type))
    }

    fun implements(type: KibbleType)
}
