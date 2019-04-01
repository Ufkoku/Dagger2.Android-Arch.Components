package com.ufkoku.daggernewway.domain.ui.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(val street: String,
                   val suite: String,
                   val city: String,
                   val zipcode: String) : Parcelable
