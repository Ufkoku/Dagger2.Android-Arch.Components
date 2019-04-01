package com.ufkoku.daggernewway.domain.rest.entity

import com.google.gson.annotations.SerializedName

class RestCompany(@SerializedName("name") val name: String,
                  @SerializedName("catchPhrase") val catchPhrase: String,
                  @SerializedName("bs") val bs: String)