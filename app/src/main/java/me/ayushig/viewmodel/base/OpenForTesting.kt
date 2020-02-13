package me.ayushig.viewmodel.base

/**
 * Annotate a class with [OpenForTesting] if you want it to be extendable in debug builds.
 * This annotation allows us to open some classes for mocking purposes while they are final in
 * release builds.
 */

@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class OpenClass

@OpenClass
@Target(AnnotationTarget.CLASS)
annotation class OpenForTesting