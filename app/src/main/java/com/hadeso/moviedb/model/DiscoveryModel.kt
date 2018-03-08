package com.hadeso.moviedb.model

import com.squareup.moshi.Json

/**
 * Created by 77796 on 07-Mar-18.
 */
data class DiscoveryModel(
        val page: Int,
        @Json(name = "total_results") val totalResults: Int,
        @Json(name = "total_pages") val totalPages: Int,
        val results: List<MovieModel>
)