package com.hadeso.moviedb.feature.discovery.view

import com.hadeso.moviedb.architecture.base.Intent

sealed class DiscoveryIntent : Intent {
    object Initial : DiscoveryIntent()
    object ViewResumed : DiscoveryIntent()
    data class MovieSelected(val movieId: Int, val posterUrl: String, val movieTitle: String) : DiscoveryIntent()
}
