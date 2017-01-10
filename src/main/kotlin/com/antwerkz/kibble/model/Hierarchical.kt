package com.antwerkz.kibble.model

interface Hierarchical {
    var modality: Modality

    fun isAbstract(): Boolean {
        return Modality.ABSTRACT == modality
    }

    fun isFinal(): Boolean {
        return Modality.FINAL == modality
    }

    fun isOpen(): Boolean {
        return Modality.OPEN == modality
    }
}
