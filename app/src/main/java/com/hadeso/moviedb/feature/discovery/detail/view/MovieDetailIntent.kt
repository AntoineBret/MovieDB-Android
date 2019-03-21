package com.hadeso.moviedb.feature.discovery.detail.view

import com.hadeso.moviedb.architecture.base.Intent

sealed class MovieDetailIntent : Intent {
    data class Initial(val movieId: Int) : MovieDetailIntent()
}
