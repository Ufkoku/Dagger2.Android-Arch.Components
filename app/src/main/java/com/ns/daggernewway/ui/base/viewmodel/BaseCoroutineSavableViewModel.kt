package com.ns.daggernewway.ui.base.viewmodel

import android.os.Bundle
import androidx.annotation.CallSuper
import com.ufkoku.archcomponents.SavableViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseCoroutineSavableViewModel : SavableViewModel, CoroutineScope {

    private val rootJob = Job()

    override val coroutineContext: CoroutineContext = rootJob + Dispatchers.Main

    constructor(bundleSuffix: String = DEFAULT_SUFFIX) : super(bundleSuffix)

    constructor(bundleSuffix: String = DEFAULT_SUFFIX, savedInstanceState: Bundle) : super(bundleSuffix, savedInstanceState)

    @CallSuper
    override fun onCleared() {
        rootJob.cancel()
        super.onCleared()
    }

}