package com.ufkoku.archcomponents.utils

import android.app.Activity
import android.app.Instrumentation
import androidx.test.rule.ActivityTestRule

fun <T : Activity> ActivityTestRule<T>.waitForActivityCreation(): T {
    while (activity == null) {
        Thread.sleep(100)
    }
    return activity
}

fun <T : Activity> ActivityTestRule<T>.recreateActivitySync(instrumentation: Instrumentation): T {
    while (this.activity == null) {
        Thread.sleep(100)
    }

    val activity = activity
    instrumentation.runOnMainSync {
        activity.recreate()
    }
    while (this.activity == activity) {
        Thread.sleep(100)
    }

    return this.activity
}