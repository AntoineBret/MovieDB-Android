package com.hadeso.moviedb.feature.discovery.detail.domain

import com.hadeso.moviedb.architecture.base.Command

sealed class MovieDetailCommand : Command {
    data class LoadMovieDetail(val movieId: Int) : MovieDetailCommand()
}
