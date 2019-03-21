package com.hadeso.moviedb.feature.discovery.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hadeso.moviedb.architecture.Store
import com.hadeso.moviedb.architecture.base.Action
import com.hadeso.moviedb.architecture.base.IntentInterpreter
import com.hadeso.moviedb.core.state.AppState
import com.hadeso.moviedb.core.view.BaseViewModel
import com.hadeso.moviedb.feature.discovery.domain.DiscoveryCommand
import com.hadeso.moviedb.feature.discovery.domain.DiscoveryUseCase
import com.hadeso.moviedb.feature.discovery.state.DiscoveryState
import com.hadeso.moviedb.feature.discovery.state.discoveryLens
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class DiscoveryViewModel @Inject constructor(
    private val discoveryUseCase: DiscoveryUseCase,
    private val store: Store<AppState>
) : BaseViewModel(),
    IntentInterpreter<DiscoveryIntent, DiscoveryCommand, DiscoveryState> {

    private val stateLiveData: MutableLiveData<DiscoveryState> = MutableLiveData()

    override fun processIntents(intentObservable: Observable<DiscoveryIntent>) {
        interpret(intentObservable)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { state -> stateLiveData.value = state },
                { throwable -> Timber.d(throwable) }
            ).addTo(disposable)
    }

    override fun command(intentObservable: Observable<DiscoveryIntent>): Observable<DiscoveryCommand> {
        return intentObservable
            .observeOn(Schedulers.computation())
            .flatMapIterable { intent ->
                Timber.d("Received intent : ${intent::class.java.simpleName}")
                return@flatMapIterable when (intent) {
                    DiscoveryIntent.Initial -> listOf(DiscoveryCommand.LoadMovies)
                    is DiscoveryIntent.MovieSelected -> listOf(DiscoveryCommand.GoToDetail(intent.movieId))
                }
            }
    }

    override fun action(commandObservable: Observable<DiscoveryCommand>): Observable<Action> {
        return discoveryUseCase.execute(commandObservable)
    }

    override fun state(actionObservable: Observable<Action>): Observable<DiscoveryState> {
        return store.dispatch(actionObservable, discoveryLens::get)
    }

    fun stateLiveData(): LiveData<DiscoveryState> {
        return stateLiveData
    }
}
