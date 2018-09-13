package com.ns.archcomponents.annotations

@Target(AnnotationTarget.CONSTRUCTOR)
@Retention(AnnotationRetention.SOURCE)
annotation class ConstructorPriority(val priority: Int)