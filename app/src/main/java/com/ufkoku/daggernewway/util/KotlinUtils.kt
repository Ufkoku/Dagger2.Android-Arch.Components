package com.ufkoku.daggernewway.util

fun <T> fLazy(initializer: () -> T) = lazy(LazyThreadSafetyMode.NONE, initializer)