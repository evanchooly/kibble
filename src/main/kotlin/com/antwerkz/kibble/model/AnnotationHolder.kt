package com.antwerkz.kibble.model

/**
 * Marks a type as supporting annotations
 *
 * @property annotations the list of annotations on this type
 */
interface AnnotationHolder {
    val annotations: List<KibbleAnnotation>

    @Suppress("UNCHECKED_CAST")
    fun addAnnotation(type: Class<out Annotation>, arguments: List<KibbleArgument> = listOf()) {
        addAnnotation(KibbleAnnotation(KibbleType.from(type as Class<Any>), arguments))
    }

    fun addAnnotation(type: String, arguments: List<KibbleArgument> = listOf()) {
        addAnnotation(KibbleAnnotation(KibbleType.from(type), arguments))
    }

    fun addAnnotation(annotation: KibbleAnnotation)

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
            it.type.fqcn() == annotation.name || it.type.isAutoImported() && (it.type.resolvedName == annotation.simpleName)
        }
    }
}