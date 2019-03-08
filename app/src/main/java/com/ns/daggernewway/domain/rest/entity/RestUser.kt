package com.ns.daggernewway.domain.rest.entity

import com.google.gson.annotations.SerializedName

class RestUser(@SerializedName("id") val id: Int,
               @SerializedName("name") val name: String,
               @SerializedName("username") val username: String,
               @SerializedName("email") val email: String,
               @SerializedName("address") val address: RestAddress,
               @SerializedName("phone") val phone: String,
               @SerializedName("website") val website: String,
               @SerializedName("company") val company: RestCompany)