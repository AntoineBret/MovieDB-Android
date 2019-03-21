package com.hadeso.moviedb.feature.discovery.detail.domain

sealed class MovieDetailError {
    object Network : MovieDetailError()
}
