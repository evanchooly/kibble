package com.antwerkz.kibble.model

import com.antwerkz.kibble.model.KibbleAnnotation.Companion.from
import org.jetbrains.kotlin.psi.KtAnnotationEntry

/**
 * Marks a type as supporting annotations
 *
 * @property annotations the list of annotations on this type
 */
interface Annotatable {
    var annotations: MutableList<KibbleAnnotation>

    /**
     * Checks if this type has been annotated with a given Annotation
     */
    fun hasAnnotation(annotation: Class<out Annotation>): Boolean {
        return getAnnotation(annotation) != null
    }

    /**
     * Gets the instance of the requested if present
     */
    fun getAnnotation(annotation: Class<out Annotation>): KibbleAnnotation? {
        return annotations.firstOrNull {
            it.name == annotation.simpleName || it.name == annotation.name
        }
    }
}

internal fun Annotatable.extractAnnotations(entries: List<KtAnnotationEntry>) {
    annotations.addAll(entries.map { from(it) })
}