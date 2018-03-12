package com.hadeso.moviedb.ui.discovery

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.hadeso.moviedb.model.MovieModel
import com.hadeso.moviedb.repository.MovieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by 77796 on 21-Dec-17.
 */
class DiscoveryViewModel @Inject constructor(val movieRepository: MovieRepository) : ViewModel() {

    private val movies: MutableLiveData<List<DiscoveryViewItem>> = MutableLiveData()

    fun init() {
        movieRepository.getDiscoveryMovies().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { model -> modelToView(model) }
                .toList()
                .subscribe({ views ->
                    movies.value = views
                }) { throwable ->
                    Timber.e(throwable.message)
                }
    }

    fun getMovies(): MutableLiveData<List<DiscoveryViewItem>> {
        return movies
    }

    private fun modelToView(movieModel: MovieModel): DiscoveryViewItem {
        return DiscoveryViewItem(movieModel.id,
                movieModel.title,
                movieModel.posterPath,
                movieModel.overview,
                movieModel.voteAverage.toString())
    }

}