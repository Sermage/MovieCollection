package com.sermage.mymoviecollection.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class ProductionCompany(
    @SerializedName("id")
    @Expose
    val id: Int = 0,

    @SerializedName("logo_path")
    @Expose
    val logoPath: Any? = null,

    @SerializedName("name")
    @Expose
    val name: String? = null,

    @SerializedName("origin_country")
    @Expose
    val originCountry: String? = null
)