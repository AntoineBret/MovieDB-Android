package com.hadeso.moviedb.repository

import com.hadeso.moviedb.api.MovieDBService
import com.hadeso.moviedb.model.DiscoveryMovieModel
import com.hadeso.moviedb.model.MovieModel
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by 77796 on 07-Mar-18.
 */
class MovieRepository @Inject constructor(val movieDBService: MovieDBService) {

    fun getDiscoveryMovies(): Observable<DiscoveryMovieModel> {
        return movieDBService.discover()
                .flatMapObservable { model -> Observable.fromIterable(model.results) }
    }

    fun getMovie(movieId: Int): Single<MovieModel> {
        return movieDBService.getMovie(movieId)
    }
}