package com.hadeso.moviedb.feature.discovery.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hadeso.moviedb.architecture.Store
import com.hadeso.moviedb.architecture.base.Action
import com.hadeso.moviedb.architecture.base.IntentInterpreter
import com.hadeso.moviedb.core.state.AppState
import com.hadeso.moviedb.core.view.BaseViewModel
import com.hadeso.moviedb.feature.discovery.domain.MovieRepository
import com.hadeso.moviedb.feature.discovery.domain.goToDetails
import com.hadeso.moviedb.feature.discovery.domain.loadMovies
import com.hadeso.moviedb.feature.discovery.domain.resumeLastState
import com.hadeso.moviedb.feature.discovery.state.DiscoveryState
import com.hadeso.moviedb.feature.discovery.state.discoveryLens
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class DiscoveryViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val store: Store<AppState>
) : BaseViewModel(),
    IntentInterpreter<DiscoveryIntent, DiscoveryState> {

    private val stateLiveData: MutableLiveData<DiscoveryState> = MutableLiveData()

    override fun processIntents(intentObservable: Observable<DiscoveryIntent>) {
        interpret(intentObservable)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { state -> stateLiveData.value = state },
                { throwable -> Timber.d(throwable) }
            ).addTo(disposable)
    }

    override fun action(intentObservable: Observable<DiscoveryIntent>): Observable<Action> {
        return intentObservable
            .observeOn(Schedulers.computation())
            .flatMap { intent ->
                Timber.d("Received intent : ${intent::class.java.simpleName}")
                return@flatMap when (intent) {
                    DiscoveryIntent.Initial -> loadMovies(movieRepository)
                    DiscoveryIntent.ViewResumed -> resumeLastState()
                    is DiscoveryIntent.MovieSelected -> goToDetails(intent.movieId, intent.posterUrl, intent.movieTitle)
                }
            }
    }

    override fun state(actionObservable: Observable<Action>): Observable<DiscoveryState> {
        return store.dispatch(actionObservable, discoveryLens::get)
    }

    fun stateLiveData(): LiveData<DiscoveryState> {
        return stateLiveData
    }
}
