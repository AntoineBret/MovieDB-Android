package com.hadeso.moviedb.feature.discovery.state

import com.hadeso.moviedb.architecture.base.State
import com.hadeso.moviedb.feature.discovery.domain.DiscoveryError
import com.hadeso.moviedb.feature.discovery.view.DiscoveryViewItem

sealed class DiscoveryState : State {
    abstract val data: DiscoveryData

    data class Idle(override val data: DiscoveryData) : DiscoveryState()
    data class Loading(override val data: DiscoveryData) : DiscoveryState()
    data class MoviesLoaded(override val data: DiscoveryData) : DiscoveryState()
    data class MovieNavigation(override val data: DiscoveryData, val movieId: Int) : DiscoveryState()
    data class Error(override val data: DiscoveryData) : DiscoveryState()
}

data class DiscoveryData(
    val discoveryMovies: List<DiscoveryViewItem> = listOf(),
    val error: DiscoveryError? = null
)
