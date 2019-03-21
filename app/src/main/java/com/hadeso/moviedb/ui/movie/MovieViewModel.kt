package com.hadeso.moviedb.ui.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hadeso.moviedb.model.MovieModel
import com.hadeso.moviedb.repository.MovieRepository
import com.hadeso.moviedb.ui.discovery.DiscoveryViewItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class MovieViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    private val movie: MutableLiveData<MovieModel> = MutableLiveData()

    fun init(discoveryMovie: DiscoveryViewItem) {
        movieRepository.getMovie(discoveryMovie.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ movie ->
                    this.movie.value = movie
                }) { throwable ->
                    Timber.e(throwable.message)
                }
    }

    fun getMovie(): MutableLiveData<MovieModel> {
        return movie
    }

}
