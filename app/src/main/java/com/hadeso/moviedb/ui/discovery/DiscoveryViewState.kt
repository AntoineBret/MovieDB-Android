package com.hadeso.moviedb.ui.discovery

import com.hadeso.moviedb.mvibase.MviViewState


data class DiscoveryViewState(
        val isLoading: Boolean,
        val movies: List<DiscoveryViewItem>,
        val error: Throwable?
) : MviViewState {
    companion object {
        fun idle(): DiscoveryViewState {
            return DiscoveryViewState(
                    isLoading = false,
                    movies = emptyList(),
                    error = null
            )
        }
    }
}