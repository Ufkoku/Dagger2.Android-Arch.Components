package com.ns.daggernewway.ui.base.viewmodel

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseCoroutineViewModel : ViewModel(), CoroutineScope {

    private val rootJob = Job()

    override val coroutineContext: CoroutineContext = rootJob + Dispatchers.Main

    @CallSuper
    override fun onCleared() {
        rootJob.cancel()
        super.onCleared()
    }

}
