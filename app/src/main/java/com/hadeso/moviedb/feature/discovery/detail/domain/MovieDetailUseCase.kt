package com.hadeso.moviedb.feature.discovery.detail.domain

import com.hadeso.moviedb.architecture.base.Action
import com.hadeso.moviedb.feature.discovery.detail.state.MovieDetailAction
import com.hadeso.moviedb.feature.discovery.detail.view.MovieDetailViewItem
import com.hadeso.moviedb.feature.discovery.domain.MovieRepository
import com.hadeso.moviedb.model.MovieModel
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

fun loadMovieDetail(movieRepository: MovieRepository, movieId: Int): Observable<Action> {
    return movieRepository.getMovie(movieId)
        .map { modelToViewItem(it) }
        .map { MovieDetailAction.UpdateMovieDetail(it) }
        .cast(Action::class.java)
        .toObservable()
        .startWith(MovieDetailAction.StartLoad)
        .onErrorReturn { MovieDetailAction.Error(MovieDetailError.Network) }
        .subscribeOn(Schedulers.io())
}

fun modelToViewItem(movieModel: MovieModel): MovieDetailViewItem {
    return MovieDetailViewItem(
        movieModel.id,
        movieModel.title,
        movieModel.overview,
        movieModel.posterPath,
        movieModel.backdropPath
    )
}
