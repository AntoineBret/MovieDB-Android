package com.hadeso.moviedb.feature.discovery.detail.state

import com.hadeso.moviedb.architecture.base.Action
import com.hadeso.moviedb.feature.discovery.detail.domain.MovieDetailError
import com.hadeso.moviedb.feature.discovery.detail.view.MovieDetailViewItem
import com.hadeso.moviedb.model.MovieModel

sealed class MovieDetailAction : Action {
    object StartLoad : MovieDetailAction()
    data class UpdateMovieDetail(val movieDetailViewItem: MovieDetailViewItem) : MovieDetailAction()
    data class Error(val error: MovieDetailError) : MovieDetailAction()
}
