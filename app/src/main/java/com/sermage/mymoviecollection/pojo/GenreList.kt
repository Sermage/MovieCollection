package com.sermage.mymoviecollection.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class GenreList {
    @SerializedName("genres")
    @Expose
    val genres: List<Genre>? = null
}