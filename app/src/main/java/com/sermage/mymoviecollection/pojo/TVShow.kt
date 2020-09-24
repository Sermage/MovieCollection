package com.sermage.mymoviecollection.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import com.sermage.mymoviecollection.converters.Converters
import java.io.Serializable


@Entity(tableName = "favorite_tv_shows")
@TypeConverters(Converters::class)
data class TVShow(
    @SerializedName("original_name")
    @Expose
    val originalName: String? = null,

    @PrimaryKey
    @SerializedName("id")
    @Expose
    val id: Int = 0,

    @SerializedName("name")
    @Expose
    val name: String? = null,

    @SerializedName("vote_count")
    @Expose
    val voteCount: Int = 0,

    @SerializedName("vote_average")
    @Expose
    val voteAverage: Double = 0.0,

    @SerializedName("first_air_date")
    @Expose
    val firstAirDate: String? = null,

    @SerializedName("poster_path")
    @Expose
    val posterPath: String? = null,

    @SerializedName("genre_ids")
    @Expose
    val genreIds: List<Int>? = null,

    @SerializedName("original_language")
    @Expose
    val originalLanguage: String? = null,

    @SerializedName("backdrop_path")
    @Expose
    val backdropPath: String? = null,

    @SerializedName("overview")
    @Expose
    val overview: String? = null,

    @SerializedName("origin_country")
    @Expose
    val originCountry: List<String>? = null,

    @SerializedName("popularity")
    @Expose
    val popularity: Double = 0.0,

    var isFavorite: Boolean? = false
) : Serializable