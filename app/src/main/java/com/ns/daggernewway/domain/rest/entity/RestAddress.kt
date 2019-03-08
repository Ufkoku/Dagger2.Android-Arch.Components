package com.ns.daggernewway.domain.rest.entity

import com.google.gson.annotations.SerializedName

class RestAddress(@SerializedName("street") val street: String,
                  @SerializedName("suite") val suite: String,
                  @SerializedName("city") val city: String,
                  @SerializedName("zipcode") val zipcode: String)