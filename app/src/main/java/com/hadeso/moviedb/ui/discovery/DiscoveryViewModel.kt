package com.hadeso.moviedb.ui.discovery

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.hadeso.moviedb.model.DiscoveryMovieModel
import com.hadeso.moviedb.mvibase.MviViewModel
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class DiscoveryViewModel @Inject constructor(
        private val actionProcessorHolder: DiscoveryActionProcessorHolder)
    : ViewModel(), MviViewModel<DiscoveryIntent, DiscoveryViewState> {

    private val intentsSubject: PublishSubject<DiscoveryIntent> = PublishSubject.create()
    private val viewState: MutableLiveData<DiscoveryViewState> = MutableLiveData()
    private val statesObservable: Observable<DiscoveryViewState> = compose()

    override fun processIntents(intents: Observable<DiscoveryIntent>) {
        statesObservable.subscribe({ state ->
            viewState.value = state
        })
        intents.subscribe(intentsSubject)
    }

    override fun states(): LiveData<DiscoveryViewState> {
        return viewState
    }

    private fun compose(): Observable<DiscoveryViewState> {
        return intentsSubject
                .map(this::actionFromIntent)
                .compose(actionProcessorHolder.actionProcessor)
                .scan(DiscoveryViewState.idle(), reducer)
                .distinctUntilChanged()
                .replay(1)
                .autoConnect(0)
    }

    private fun actionFromIntent(intent: DiscoveryIntent): DiscoveryAction {
        return when (intent) {
            is DiscoveryIntent.InitialIntent -> DiscoveryAction.LoadDiscoveryAction
            is DiscoveryIntent.MovieSelected -> DiscoveryAction.GoToMovieDetailAction(intent.discoveryViewItem)

        }
    }

    companion object {
        private val reducer = BiFunction { previousState: DiscoveryViewState, result: DiscoveryResult ->
        when (result) {
            is DiscoveryResult.LoadDiscoveryMoviesResult.Success ->
        previousState.copy(isLoading = false,
                error = null,
                movies = result.movies.results.map { modelToView(it) })
            is DiscoveryResult.LoadDiscoveryMoviesResult.Failure ->
                previousState.copy(isLoading = false, error = result.error)
            is DiscoveryResult.GotoMovieResult.Success ->
                previousState.copy(isLoading = false,
                        error = null,
                        movies = result.movies.results.map { modelToView(it) })
            is DiscoveryResult.GotoMovieResult.Failure ->
                previousState.copy(isLoading = false, error = result.error)
        }
    }


        private fun modelToView(discoveryMovieModel: DiscoveryMovieModel): DiscoveryViewItem {
            return DiscoveryViewItem(discoveryMovieModel.id,
                    discoveryMovieModel.title,
                    discoveryMovieModel.posterPath,
                    discoveryMovieModel.overview,
                    discoveryMovieModel.voteAverage.toString())
        }
    }
}