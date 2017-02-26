package com.antwerkz.kibble.model

import com.antwerkz.kibble.model.KotlinAnnotation.Companion.from
import org.jetbrains.kotlin.psi.KtAnnotationEntry

interface Annotatable {
    var annotations: MutableList<KotlinAnnotation>

    fun extract(entries: List<KtAnnotationEntry>) {
        annotations.addAll(entries.map { from(it) })
    }

    fun extract(entry: KtAnnotationEntry) {
        annotations.add(from(entry))
    }

    fun hasAnnotation(annotation: Class<out Annotation>): Boolean {
        return annotations.firstOrNull {
            it.name == annotation.simpleName || it.name == annotation.name
        } != null
    }
}
