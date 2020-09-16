package com.sermage.mymoviecollection.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




data class Genre (
    @SerializedName("id")
    @Expose
    val id:Int = 0,

    @SerializedName("name")
    @Expose
    val name: String? = null
)