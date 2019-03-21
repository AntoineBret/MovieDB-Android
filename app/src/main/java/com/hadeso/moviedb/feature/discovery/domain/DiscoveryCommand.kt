package com.hadeso.moviedb.feature.discovery.domain

import com.hadeso.moviedb.architecture.base.Command
import com.hadeso.moviedb.feature.discovery.view.DiscoveryViewItem

sealed class DiscoveryCommand : Command {
    object LoadMovies : DiscoveryCommand()
    data class GoToDetail(val viewItem: DiscoveryViewItem) : DiscoveryCommand()
}
