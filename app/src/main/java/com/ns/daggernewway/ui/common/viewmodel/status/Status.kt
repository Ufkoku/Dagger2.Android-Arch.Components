package com.ns.daggernewway.ui.common.viewmodel.status

@Suppress("DataClassPrivateConstructor")
data class Status<T> private constructor(override val state: IStatus.State,
                                         override val errorMessage: String?,
                                         override val data: T?) : IStatus<T> {

    companion object {

        fun <T> idle(): Status<T> = Status(IStatus.State.IDLE, null, null)

        fun <T> inProgress(): Status<T> = Status(IStatus.State.IN_PROGRESS, null, null)

        fun <T> completed(data: T): Status<T> = Status(IStatus.State.COMPLETED, null, data)

        fun <T> error(msg: String): Status<T> = Status(IStatus.State.ERROR, msg, null)

    }

}
