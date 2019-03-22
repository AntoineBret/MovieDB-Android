package com.hadeso.moviedb.feature.discovery.detail.state

import com.hadeso.moviedb.architecture.Mutator
import com.hadeso.moviedb.architecture.base.Action
import com.hadeso.moviedb.architecture.base.Lens
import com.hadeso.moviedb.core.state.AppState
import timber.log.Timber

val movieDetailLens = object : Lens<AppState, MovieDetailState> {
    override fun get(s: AppState): MovieDetailState = s.movieDetailState
    override fun set(s: AppState, a: MovieDetailState): AppState = s.copy(movieDetailState = a)
}

val movieDetailMutator = Mutator(movieDetailLens, ::movieDetailReducer)

private fun movieDetailReducer(state: AppState, action: Action): MovieDetailState {
    val previousState = movieDetailLens.get(state)

    return when (action) {
        is MovieDetailAction -> reduceMovieDetailAction(action, previousState)
        else -> state.movieDetailState
    }
}

private fun reduceMovieDetailAction(action: MovieDetailAction, previousState: MovieDetailState): MovieDetailState {
    Timber.d("Received action : ${action::class.java.simpleName}")
    return when (action) {
        MovieDetailAction.StartLoad -> MovieDetailState.Loading(previousState.data)
        is MovieDetailAction.UpdateMovieDetail -> MovieDetailState.DetailsLoaded(previousState.data.copy(movieDetailViewItem = action.movieDetailViewItem))
        is MovieDetailAction.Error -> MovieDetailState.Error(previousState.data.copy(error = action.error))
    }
}
