package com.hadeso.moviedb.ui.discovery

import com.hadeso.moviedb.repository.MovieRepository
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DiscoveryActionProcessorHolder @Inject constructor(private val movieRepository: MovieRepository) {

    private val loadDiscoveryMoviesProcessor =
            ObservableTransformer<DiscoveryAction, DiscoveryResult> { actions ->
                actions.flatMap { _ ->
                    movieRepository.getDiscoveryMovies()
                            .toObservable()
                            .map { discoveryListModel ->
                                DiscoveryResult.LoadDiscoveryMoviesResult.Success(discoveryListModel)
                            }
                            .cast(DiscoveryResult.LoadDiscoveryMoviesResult::class.java)
                            .onErrorReturn { throwable: Throwable ->
                                DiscoveryResult.LoadDiscoveryMoviesResult.Failure(throwable)
                            }
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                }
            }

    internal var actionProcessor =
            ObservableTransformer<DiscoveryAction, DiscoveryResult> { actions ->
                actions.publish { shared ->
                    shared.ofType(DiscoveryAction.LoadDiscoveryAction::class.java).compose(loadDiscoveryMoviesProcessor)
                }
            }
}