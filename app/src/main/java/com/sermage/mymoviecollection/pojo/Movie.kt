package com.sermage.mymoviecollection.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.sermage.mymoviecollection.converters.Converters
import java.io.Serializable

@Entity(tableName = "favorite_movies")
@TypeConverters(Converters::class)
data class Movie(
        @PrimaryKey
        @SerializedName("id")
        @Expose
        val id: Int? = null,

        @SerializedName("vote_count")
        @Expose
        val voteCount: Int? = null,

        @SerializedName("poster_path")
        @Expose
        val posterPath: String? = null,

        @SerializedName("backdrop_path")
        @Expose
        val backdropPath: String? = null,

        @SerializedName("genre_ids")
        @Expose
        val genreIds: List<Int>? = null,

        @SerializedName("original_title")
        @Expose
        val originalTitle: String? = null,

        @SerializedName("title")
        @Expose
        val title: String? = null,

        @SerializedName("vote_average")
        @Expose
        val voteAverage: Double? = null,

        @SerializedName("overview")
        @Expose
        val overview: String? = null,

        @SerializedName("release_date")
        @Expose
        val releaseDate: String? = null,

        var isFavorite: Boolean? = false,

        ) : Serializable
