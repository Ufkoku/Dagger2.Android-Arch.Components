package com.ns.daggernewway.domain.rest.entity

import com.google.gson.annotations.SerializedName

class RestComment(@SerializedName("postId") val postId: Int,
                  @SerializedName("id") val id: Int,
                  @SerializedName("name") val name: String,
                  @SerializedName("email") val email: String,
                  @SerializedName("body") val body: String)