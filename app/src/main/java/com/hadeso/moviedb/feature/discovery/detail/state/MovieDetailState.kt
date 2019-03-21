package com.hadeso.moviedb.feature.discovery.detail.state

import com.hadeso.moviedb.architecture.base.State
import com.hadeso.moviedb.feature.discovery.detail.domain.MovieDetailError
import com.hadeso.moviedb.feature.discovery.detail.view.MovieDetailViewItem

sealed class MovieDetailState : State {
    object Idle : MovieDetailState()
    object Loading : MovieDetailState()
    data class DetailsLoaded(val movieDetailViewItem: MovieDetailViewItem) : MovieDetailState()
    data class Error(val error: MovieDetailError) : MovieDetailState()
}
