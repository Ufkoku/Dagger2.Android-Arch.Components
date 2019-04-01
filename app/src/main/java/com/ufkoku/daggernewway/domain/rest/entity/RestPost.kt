package com.ufkoku.daggernewway.domain.rest.entity

import com.google.gson.annotations.SerializedName

class RestPost(@SerializedName("id") val id: Int,
               @SerializedName("userId") val userId: Int,
               @SerializedName("title") val title: String,
               @SerializedName("body") val body: String)