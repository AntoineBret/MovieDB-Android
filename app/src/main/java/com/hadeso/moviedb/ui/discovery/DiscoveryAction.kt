package com.hadeso.moviedb.ui.discovery

import com.hadeso.moviedb.mvibase.MviAction

sealed class DiscoveryAction : MviAction {
    object LoadDiscoveryAction : DiscoveryAction()
    data class GoToMovieDetailAction(val discoveryViewItem: DiscoveryViewItem) : DiscoveryAction()
}