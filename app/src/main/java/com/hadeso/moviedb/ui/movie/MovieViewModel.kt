package com.hadeso.moviedb.ui.movie

import android.arch.lifecycle.ViewModel
import com.hadeso.moviedb.repository.MovieRepository
import com.hadeso.moviedb.ui.discovery.DiscoveryViewItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class MovieViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    fun init(movie: DiscoveryViewItem) {
        movieRepository.getMovie(movie.id)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ views ->

                }) { throwable ->
                    Timber.e(throwable.message)
                }
    }

}