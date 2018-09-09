package com.ufkoku.archcomponents.utils

import androidx.fragment.app.Fragment

fun Fragment.waitForFragmentResumed() {
    while (!isResumed) {
        Thread.sleep(100)
    }
}