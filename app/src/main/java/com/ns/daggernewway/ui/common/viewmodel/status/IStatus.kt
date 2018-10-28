package com.ns.daggernewway.ui.common.viewmodel.status

interface IStatus<T> {

    val state: State

    val errorMessage: String?

    val data: T?

    enum class State {
        IDLE,
        IN_PROGRESS,
        COMPLETED,
        ERROR
    }

}