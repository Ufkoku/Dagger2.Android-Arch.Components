package com.ufkoku.archcomponents.annotations

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class GenerateFactory(val inject: Boolean = false)