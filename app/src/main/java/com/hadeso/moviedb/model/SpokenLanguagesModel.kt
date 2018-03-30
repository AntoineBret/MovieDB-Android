package com.hadeso.moviedb.model

import com.squareup.moshi.Json

data class SpokenLanguagesModel(
        val name: String = "",
        @Json(name = "iso_639_1") val iso: String = ""
)