package com.hadeso.moviedb.feature.discovery.detail.view

import com.hadeso.moviedb.architecture.base.Intent
import com.hadeso.moviedb.feature.discovery.view.DiscoveryViewItem

sealed class MovieDetailIntent : Intent {
    data class Initial(val viewItem: DiscoveryViewItem) : MovieDetailIntent()
}
