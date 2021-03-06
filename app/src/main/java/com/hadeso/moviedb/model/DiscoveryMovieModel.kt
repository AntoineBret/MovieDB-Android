package com.hadeso.moviedb.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DiscoveryMovieModel(
        val id: Int,
        val video: Boolean,
        val title: String,
        val overview: String,
        val popularity: Float,
        val adult: Boolean,
        @Json(name = "genre_ids") val genre: IntArray,
        @Json(name = "vote_count") val voteCount: Int,
        @Json(name = "vote_average") val voteAverage: Float,
        @Json(name = "poster_path") val posterPath: String,
        @Json(name = "backdrop_path") val backdropPath: String,
        @Json(name = "original_language") val originalLanguage: String,
        @Json(name = "original_title") val originalTitle: String,
        @Json(name = "release_date") val releaseDate: String

) : Parcelable

