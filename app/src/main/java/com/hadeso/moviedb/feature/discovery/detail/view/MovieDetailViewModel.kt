package com.hadeso.moviedb.feature.discovery.detail.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hadeso.moviedb.architecture.Store
import com.hadeso.moviedb.architecture.base.Action
import com.hadeso.moviedb.architecture.base.IntentInterpreter
import com.hadeso.moviedb.core.state.AppState
import com.hadeso.moviedb.core.view.BaseViewModel
import com.hadeso.moviedb.feature.discovery.detail.domain.MovieDetailCommand
import com.hadeso.moviedb.feature.discovery.detail.domain.MovieDetailUseCase
import com.hadeso.moviedb.feature.discovery.detail.state.MovieDetailState
import com.hadeso.moviedb.feature.discovery.detail.state.movieDetailLens
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(private val movieDetailUseCase: MovieDetailUseCase, private val store: Store<AppState>) : BaseViewModel(),
    IntentInterpreter<MovieDetailIntent, MovieDetailCommand, MovieDetailState> {

    private val stateLiveData: MutableLiveData<MovieDetailState> = MutableLiveData()

    override fun processIntents(intentObservable: Observable<MovieDetailIntent>) {
        interpret(intentObservable)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { state -> stateLiveData.value = state },
                { throwable -> Timber.d(throwable) }
            ).addTo(disposable)
    }

    override fun command(intentObservable: Observable<MovieDetailIntent>): Observable<MovieDetailCommand> {
        return intentObservable
            .observeOn(Schedulers.computation())
            .flatMapIterable { intent ->
                Timber.d("Received intent : ${intent::class.java.simpleName}")
                return@flatMapIterable when (intent) {
                    is MovieDetailIntent.Initial -> listOf(MovieDetailCommand.LoadMovieDetail(intent.viewItem.id))
                }
            }
    }

    override fun action(commandObservable: Observable<MovieDetailCommand>): Observable<Action> {
        return movieDetailUseCase.execute(commandObservable)
    }

    override fun state(actionObservable: Observable<Action>): Observable<MovieDetailState> {
        return store.dispatch(actionObservable, movieDetailLens::get)
    }

    fun stateLiveData(): LiveData<MovieDetailState> {
        return stateLiveData
    }
}
