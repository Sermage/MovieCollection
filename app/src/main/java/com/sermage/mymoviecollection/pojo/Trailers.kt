package com.sermage.mymoviecollection.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Trailers (
    @SerializedName("key")
    @Expose
    val key: String? = null,

    @SerializedName("name")
    @Expose
    val name: String? = null
)