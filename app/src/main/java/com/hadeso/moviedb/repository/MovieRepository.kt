package com.hadeso.moviedb.repository

import com.hadeso.moviedb.api.MovieDBService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by 77796 on 07-Mar-18.
 */
class MovieRepository @Inject constructor(val movieDBService: MovieDBService) {

    fun getDiscoveryMovies() {

        movieDBService.discover()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ model ->

                }) { throwable ->
                    Timber.e(throwable.message)
                }
    }
}