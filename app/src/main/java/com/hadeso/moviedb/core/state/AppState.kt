package com.hadeso.moviedb.core.state

import com.hadeso.moviedb.architecture.base.State
import com.hadeso.moviedb.feature.discovery.detail.state.MovieDetailState
import com.hadeso.moviedb.feature.discovery.state.DiscoveryState

data class AppState(
    val discoveryState: DiscoveryState,
    val movieDetailState: MovieDetailState
) : State
