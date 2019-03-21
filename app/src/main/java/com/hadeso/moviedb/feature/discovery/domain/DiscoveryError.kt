package com.hadeso.moviedb.feature.discovery.domain

sealed class DiscoveryError {
    object Network : DiscoveryError()
}
