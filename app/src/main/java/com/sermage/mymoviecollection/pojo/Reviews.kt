package com.sermage.mymoviecollection.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Reviews (
    @SerializedName("author")
    @Expose
    val author: String? = null,

    @SerializedName("content")
    @Expose
    val content: String? = null
)