package com.ufkoku.archcomponents.annotations

@Target(AnnotationTarget.CONSTRUCTOR)
@Retention(AnnotationRetention.SOURCE)
annotation class ConstructorPriority(val priority: Int)