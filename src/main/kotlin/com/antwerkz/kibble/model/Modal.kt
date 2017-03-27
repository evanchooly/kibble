package com.antwerkz.kibble.model


@Suppress("UNCHECKED_CAST")
interface Modal<T> {
    var modality: Modality

    fun isAbstract(): Boolean {
        return Modality.ABSTRACT == modality
    }

    fun markAbstract(): T {
        modality = Modality.ABSTRACT
        return this as T
    }

    fun isFinal(): Boolean {
        return Modality.FINAL == modality
    }

    fun markFinal(): T {
        modality = Modality.FINAL
        return this as T
    }

    fun isOpen(): Boolean {
        return Modality.OPEN == modality
    }

    fun markOpen(): T {
        modality = Modality.OPEN
        return this as T
    }

}
