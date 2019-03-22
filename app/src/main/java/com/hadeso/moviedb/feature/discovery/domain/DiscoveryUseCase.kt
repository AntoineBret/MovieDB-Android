package com.hadeso.moviedb.feature.discovery.domain

import com.hadeso.moviedb.architecture.base.Action
import com.hadeso.moviedb.feature.discovery.state.DiscoveryAction
import com.hadeso.moviedb.feature.discovery.view.DiscoveryViewItem
import com.hadeso.moviedb.model.DiscoveryMovieModel
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

fun resumeLastState() = Observable.fromCallable { DiscoveryAction.ResumeLastState }

fun goToDetails(movieId: Int, posterUrl: String, movieTitle: String): Observable<Action> =
    Observable.fromCallable { DiscoveryAction.GoToMovie(movieId, posterUrl, movieTitle) }

fun loadMovies(movieRepository: MovieRepository): Observable<Action> {
    return movieRepository.getDiscoveryMovies()
        .map { model -> modelToView(model) }
        .toList()
        .map { DiscoveryAction.UpdateMovies(it) }
        .cast(Action::class.java)
        .toObservable()
        .onErrorReturn { DiscoveryAction.Error(DiscoveryError.Network) }
        .subscribeOn(Schedulers.io())
        .startWith(DiscoveryAction.StartLoad)
}

private fun modelToView(discoveryMovieModel: DiscoveryMovieModel): DiscoveryViewItem {
    return DiscoveryViewItem(
        discoveryMovieModel.id,
        discoveryMovieModel.title,
        discoveryMovieModel.posterPath,
        discoveryMovieModel.overview,
        discoveryMovieModel.voteAverage.toString()
    )
}
