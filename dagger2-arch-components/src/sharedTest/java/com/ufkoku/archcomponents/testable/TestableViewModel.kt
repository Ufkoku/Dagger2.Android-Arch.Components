package com.ufkoku.archcomponents.testable

import android.os.Bundle
import com.ufkoku.archcomponents.SavableViewModel

class TestableViewModel : SavableViewModel {

    companion object {
        const val KEY_DATA = "data"
    }

    lateinit var data: String

    constructor(suffix: String = "", data: String) : super(suffix) {
        this.data = data
    }

    constructor(suffix: String = "", savedInstance: Bundle) : super(savedInstance, suffix)

    override fun saveInner(bundle: Bundle) {
        bundle.putString(KEY_DATA, data)
    }

    override fun restoreInner(bundle: Bundle) {
        data = bundle.getString(KEY_DATA)!!
    }

}