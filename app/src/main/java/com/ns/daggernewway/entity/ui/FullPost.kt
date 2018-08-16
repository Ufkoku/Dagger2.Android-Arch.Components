package com.ns.daggernewway.entity.ui

import android.os.Parcelable
import com.ns.daggernewway.entity.rest.User
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FullPost(val id: Int,
                    val user: User,
                    val title: String,
                    val body: String) : Parcelable