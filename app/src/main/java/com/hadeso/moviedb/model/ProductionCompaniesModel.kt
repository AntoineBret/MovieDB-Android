package com.hadeso.moviedb.model

import com.squareup.moshi.Json

data class ProductionCompaniesModel(
        @Json(name = "logo_path") val logoPath: String = "",
        val name: String = "",
        val id: Int = 0,
        @Json(name = "origin_country") val originCountry: String = ""
)