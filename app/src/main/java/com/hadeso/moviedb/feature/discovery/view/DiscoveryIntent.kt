package com.hadeso.moviedb.feature.discovery.view

sealed class DiscoveryIntent {
    class MovieSelected(val movieId: String) : DiscoveryIntent()
}
