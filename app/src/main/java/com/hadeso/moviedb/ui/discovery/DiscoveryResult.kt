package com.hadeso.moviedb.ui.discovery

import com.hadeso.moviedb.model.DiscoveryListModel
import com.hadeso.moviedb.mvibase.MviResult

sealed class DiscoveryResult : MviResult {
    sealed class LoadDiscoveryMoviesResult : DiscoveryResult() {
        data class Success(val movies: DiscoveryListModel) : LoadDiscoveryMoviesResult()
        data class Failure(val error: Throwable) : LoadDiscoveryMoviesResult()
    }
}