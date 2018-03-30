package com.hadeso.moviedb.model

import com.squareup.moshi.Json

data class ProductionCountriesModel(
        @Json(name = "iso_3166_1") val iso: String = "",
        val name: String = ""
)