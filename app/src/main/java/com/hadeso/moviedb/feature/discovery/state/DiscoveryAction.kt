package com.hadeso.moviedb.feature.discovery.state

import com.hadeso.moviedb.architecture.base.Action
import com.hadeso.moviedb.feature.discovery.domain.DiscoveryError
import com.hadeso.moviedb.feature.discovery.view.DiscoveryViewItem

sealed class DiscoveryAction : Action {
    object StartLoad : DiscoveryAction()
    data class UpdateMovies(val discoveryMovies: List<DiscoveryViewItem>) : DiscoveryAction()
    data class GoToMovie(val viewItem: DiscoveryViewItem) : DiscoveryAction()
    data class Error(val error: DiscoveryError) : DiscoveryAction()
}
