package com.hadeso.moviedb.model

import com.squareup.moshi.Json

data class BelongsToCollectionModel(
        @Json(name = "backdrop_path") val backdropPath: String? = "",
        val name: String? = "",
        val id: Int = 0,
        @Json(name = "poster_path") val posterPath: String? = ""
)
