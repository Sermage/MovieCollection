package com.sermage.mymoviecollection.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class ProductionCountry(

    @SerializedName("iso_3166_1")
    @Expose
    val iso31661: String? = null,

    @SerializedName("name")
    @Expose
    val name: String? = null

)