package com.hadeso.moviedb.feature.discovery.detail.view

data class MovieDetailViewItem(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String? = "",
    val backdropPath: String? = ""
)
