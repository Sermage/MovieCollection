package com.sermage.mymoviecollection.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ReviewResponse (
    @SerializedName("results")
    @Expose
    val reviews: List<Reviews>? = null
)