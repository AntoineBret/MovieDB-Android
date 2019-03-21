package com.hadeso.moviedb.feature.discovery.state

import com.hadeso.moviedb.architecture.base.State
import com.hadeso.moviedb.feature.discovery.domain.DiscoveryError
import com.hadeso.moviedb.feature.discovery.view.DiscoveryViewItem

sealed class DiscoveryState : State {
    object Idle : DiscoveryState()
    object Loading : DiscoveryState()
    data class MoviesLoaded(val discoveryMovies: List<DiscoveryViewItem>) : DiscoveryState()
    data class MovieNavigation(val viewItem: DiscoveryViewItem) : DiscoveryState()
    data class Error(val error: DiscoveryError) : DiscoveryState()
}
