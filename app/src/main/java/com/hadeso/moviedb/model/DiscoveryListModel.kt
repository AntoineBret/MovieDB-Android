package com.hadeso.moviedb.model

import com.squareup.moshi.Json

data class DiscoveryListModel(
        val page: Int,
        @Json(name = "total_results") val totalResults: Int,
        @Json(name = "total_pages") val totalPages: Int,
        val results: List<DiscoveryMovieModel>
)