package com.ufkoku.daggernewway.domain.ui.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Company(val name: String,
              val catchPhrase: String,
              val bs: String) : Parcelable