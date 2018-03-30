package com.hadeso.moviedb.ui.discovery

sealed class DiscoveryIntent {
    class MovieSelected(val movieId: String) : DiscoveryIntent()
}