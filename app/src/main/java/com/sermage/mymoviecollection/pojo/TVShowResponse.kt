package com.sermage.mymoviecollection.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TVShowResponse(
    @SerializedName("page")
    @Expose
    val page: Int = 0,

    @SerializedName("total_results")
    @Expose
    val totalResults: Int = 0,

    @SerializedName("total_pages")
    @Expose
    val totalPages: Int = 0,

    @SerializedName("results")
    @Expose
    val results: List<TVShow>? = null
)
