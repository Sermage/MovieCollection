package com.sermage.mymoviecollection.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class MovieDetails (
    @SerializedName("adult")
    @Expose
    val adult:Boolean = false,

    @SerializedName("backdrop_path")
    @Expose
    val backdropPath: String? = null,

    @SerializedName("budget")
    @Expose
    val budget:Int = 0,

    @SerializedName("genres")
    @Expose
    val genres: List<Genre>? = null,

    @SerializedName("id")
    @Expose
    val id:Int = 0,

    @SerializedName("imdb_id")
    @Expose
    val imdbId: String? = null,

    @SerializedName("original_language")
    @Expose
    val originalLanguage: String? = null,

    @SerializedName("original_title")
    @Expose
    val originalTitle: String? = null,

    @SerializedName("overview")
    @Expose
    val overview: String? = null,

    @SerializedName("popularity")
    @Expose
    val popularity:Double = 0.0,

    @SerializedName("poster_path")
    @Expose
    val posterPath: String? = null,

    @SerializedName("production_companies")
    @Expose
    val productionCompanies: List<ProductionCompany>? = null,

    @SerializedName("production_countries")
    @Expose
    val productionCountries: List<ProductionCountry>? = null,

    @SerializedName("release_date")
    @Expose
    val releaseDate: String? = null,

    @SerializedName("revenue")
    @Expose
    val revenue:Int = 0,

    @SerializedName("runtime")
    @Expose
    val runtime:Int = 0,

    @SerializedName("status")
    @Expose
    val status: String? = null,

    @SerializedName("tagline")
    @Expose
    val tagline: String? = null,

    @SerializedName("title")
    @Expose
    val title: String? = null,

    @SerializedName("video")
    @Expose
    val video:Boolean = false,

    @SerializedName("vote_average")
    @Expose
    val voteAverage:Double = 0.0,

    @SerializedName("vote_count")
    @Expose
    val voteCount:Int = 0
)