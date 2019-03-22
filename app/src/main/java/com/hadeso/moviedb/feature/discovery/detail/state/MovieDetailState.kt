package com.hadeso.moviedb.feature.discovery.detail.state

import com.hadeso.moviedb.architecture.base.State
import com.hadeso.moviedb.feature.discovery.detail.domain.MovieDetailError
import com.hadeso.moviedb.feature.discovery.detail.view.MovieDetailViewItem

sealed class MovieDetailState : State {
    abstract val data: MovieDetailData

    data class Idle(override val data: MovieDetailData) : MovieDetailState()
    data class Loading(override val data: MovieDetailData) : MovieDetailState()
    data class DetailsLoaded(override val data: MovieDetailData) : MovieDetailState()
    data class Error(override val data: MovieDetailData) : MovieDetailState()
}

data class MovieDetailData(
    val movieDetailViewItem: MovieDetailViewItem? = null,
    val error: MovieDetailError? = null
)
