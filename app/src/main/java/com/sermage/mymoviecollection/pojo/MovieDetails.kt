package com.sermage.mymoviecollection.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class MovieDetails (
    @SerializedName("adult")
    @Expose
    var adult:Boolean = false,

    @SerializedName("backdrop_path")
    @Expose
    var backdropPath: String? = null,

    @SerializedName("budget")
    @Expose
    var budget:Int = 0,

    @SerializedName("genres")
    @Expose
    var genres: List<Genre>? = null,

    @SerializedName("id")
    @Expose
    var id:Int = 0,

    @SerializedName("imdb_id")
    @Expose
    var imdbId: String? = null,

    @SerializedName("original_language")
    @Expose
    var originalLanguage: String? = null,

    @SerializedName("original_title")
    @Expose
    var originalTitle: String? = null,

    @SerializedName("overview")
    @Expose
    var overview: String? = null,

    @SerializedName("popularity")
    @Expose
    var popularity:Double = 0.0,

    @SerializedName("poster_path")
    @Expose
    var posterPath: String? = null,

    @SerializedName("production_companies")
    @Expose
    var productionCompanies: List<ProductionCompany>? = null,

    @SerializedName("production_countries")
    @Expose
    var productionCountries: List<ProductionCountry>? = null,

    @SerializedName("release_date")
    @Expose
    var releaseDate: String? = null,

    @SerializedName("revenue")
    @Expose
    var revenue:Int = 0,

    @SerializedName("runtime")
    @Expose
    var runtime:Int = 0,

    @SerializedName("status")
    @Expose
    var status: String? = null,

    @SerializedName("tagline")
    @Expose
    var tagline: String? = null,

    @SerializedName("title")
    @Expose
    var title: String? = null,

    @SerializedName("video")
    @Expose
    var video:Boolean = false,

    @SerializedName("vote_average")
    @Expose
    var voteAverage:Double = 0.0,

    @SerializedName("vote_count")
    @Expose
    var voteCount:Int = 0
)