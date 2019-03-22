package com.hadeso.moviedb.feature.discovery.state

import com.hadeso.moviedb.architecture.base.Action
import com.hadeso.moviedb.feature.discovery.domain.DiscoveryError
import com.hadeso.moviedb.feature.discovery.view.DiscoveryViewItem

sealed class DiscoveryAction : Action {
    object StartLoad : DiscoveryAction()
    object ResumeLastState : DiscoveryAction()
    data class UpdateMovies(val discoveryMovies: List<DiscoveryViewItem>) : DiscoveryAction()
    data class GoToMovie(val movieId: Int, val posterUrl: String, val movieTitle: String) : DiscoveryAction()
    data class Error(val error: DiscoveryError) : DiscoveryAction()
}
