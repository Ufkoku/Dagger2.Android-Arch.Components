package com.ns.daggernewway.domain.ui.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(val id: Int,
                val name: String,
                val username: String,
                val email: String,
                val address: Address,
                val phone: String,
                val website: String,
                val company: Company) : Parcelable