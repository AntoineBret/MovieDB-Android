package com.hadeso.moviedb.repository

import com.hadeso.moviedb.api.MovieDBService
import com.hadeso.moviedb.model.DiscoveryListModel
import com.hadeso.moviedb.model.MovieModel
import io.reactivex.Single
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieDBService: MovieDBService) {

    fun getDiscoveryMovies(): Single<DiscoveryListModel> {
        return movieDBService.discover()
    }

    fun getMovie(movieId: Int): Single<MovieModel> {
        return movieDBService.getMovie(movieId)
    }
}