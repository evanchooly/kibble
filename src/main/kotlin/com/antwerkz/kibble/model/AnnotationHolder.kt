package com.antwerkz.kibble.model

/**
 * Marks a type as supporting annotations
 *
 * @property annotations the list of annotations on this type
 */
interface AnnotationHolder {
    val annotations: MutableList<KibbleAnnotation>

    fun addAnnotation(type: Class<out Annotation>, arguments: Map<String, Any> = mapOf()) {
        addAnnotation(type.name, arguments)
    }

    @Suppress("UNCHECKED_CAST")
    fun addAnnotation(type: String, arguments: Map<String, Any> = mapOf()) {
        annotations.add(KibbleAnnotation(KibbleType.from(type), arguments))
    }

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
            it.type.className == annotation.simpleName || it.type.className == annotation.name
        }
    }
}