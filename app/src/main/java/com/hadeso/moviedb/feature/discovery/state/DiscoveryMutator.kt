package com.hadeso.moviedb.feature.discovery.state

import com.hadeso.moviedb.architecture.Mutator
import com.hadeso.moviedb.architecture.base.Action
import com.hadeso.moviedb.architecture.base.Lens
import com.hadeso.moviedb.core.state.AppState
import timber.log.Timber

val discoveryLens = object : Lens<AppState, DiscoveryState> {
    override fun get(s: AppState): DiscoveryState = s.discoveryState
    override fun set(s: AppState, a: DiscoveryState): AppState = s.copy(discoveryState = a)
}

val discoveryMutator = Mutator(discoveryLens, ::discoveryReducer)

private fun discoveryReducer(state: AppState, action: Action): DiscoveryState {
    val previousState = discoveryLens.get(state)

    return when (action) {
        is DiscoveryAction -> reduceDiscoveryAction(action, previousState)
        else -> state.discoveryState
    }
}

private fun reduceDiscoveryAction(action: DiscoveryAction, previousState: DiscoveryState): DiscoveryState {
    Timber.d("Received action : ${action::class.java.simpleName}")
    return when (action) {
        DiscoveryAction.StartLoad -> DiscoveryState.Loading(previousState.data)
        is DiscoveryAction.UpdateMovies -> DiscoveryState.MoviesLoaded(previousState.data.copy(discoveryMovies = action.discoveryMovies))
        is DiscoveryAction.GoToMovie -> DiscoveryState.MovieNavigation(previousState.data, action.movieId)
        is DiscoveryAction.Error -> DiscoveryState.Error(previousState.data.copy(error = action.error))
    }
}


