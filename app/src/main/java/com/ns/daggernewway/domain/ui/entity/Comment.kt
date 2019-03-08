package com.ns.daggernewway.domain.ui.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Comment(val postId: Int,
                   val id: Int,
                   val name: String,
                   val email: String,
                   val body: String) : Parcelable