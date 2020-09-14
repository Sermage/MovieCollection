package com.sermage.mymoviecollection.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TrailerResponse (
        @SerializedName("id")
    @Expose
    val id: Int = 0,

        @SerializedName("results")
    @Expose
    val trailers: List<Trailers>? = null
)