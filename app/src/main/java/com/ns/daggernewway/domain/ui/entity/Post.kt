package com.ns.daggernewway.domain.ui.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Post(val id: Int,
                val user: User,
                val title: String,
                val body: String) : Parcelable