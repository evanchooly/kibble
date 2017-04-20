package com.antwerkz.kibble.model

import com.antwerkz.kibble.model.KibbleAnnotation.Companion.from
import org.jetbrains.kotlin.psi.KtAnnotationEntry

interface Annotatable {
    var annotations: MutableList<KibbleAnnotation>

    fun extractAnnotation(file: KibbleFile, entries: List<KtAnnotationEntry>) {
        annotations.addAll(entries.map { from(file, it) })
    }

    fun extractAnnotation(file: KibbleFile, entry: KtAnnotationEntry) {
        annotations.add(from(file, entry))
    }

    fun hasAnnotation(annotation: Class<out Annotation>): Boolean {
        return getAnnotation(annotation) != null
    }

    fun getAnnotation(annotation: Class<out Annotation>): KibbleAnnotation? {
        return annotations.firstOrNull {
            it.name == annotation.simpleName || it.name == annotation.name
        }
    }
}
