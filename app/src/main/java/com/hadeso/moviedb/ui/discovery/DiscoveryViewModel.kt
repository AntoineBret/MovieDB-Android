package com.hadeso.moviedb.ui.discovery

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.hadeso.moviedb.mvibase.MviViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class DiscoveryViewModel @Inject constructor(
        private val actionProcessorHolder: DiscoveryActionProcessorHolder,
        private val reducer: DiscoveryReducer)
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
                .compose(reducer.reducer)
                .replay(1)
                .autoConnect(0)
    }

    private fun actionFromIntent(intent: DiscoveryIntent): DiscoveryAction {
        return when (intent) {
            is DiscoveryIntent.InitialIntent -> DiscoveryAction.LoadDiscoveryAction
            is DiscoveryIntent.MovieSelected -> DiscoveryAction.GoToMovieDetailAction(intent.discoveryViewItem)

        }
    }
}