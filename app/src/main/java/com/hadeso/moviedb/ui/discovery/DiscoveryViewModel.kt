package com.hadeso.moviedb.ui.discovery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hadeso.moviedb.model.DiscoveryMovieModel
import com.hadeso.moviedb.repository.MovieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class DiscoveryViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    private val movies: MutableLiveData<List<DiscoveryViewItem>> = MutableLiveData()

    init {
        movieRepository.getDiscoveryMovies()
                .map { model -> modelToView(model) }
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ views ->
                    movies.value = views
                }) { throwable ->
                    Timber.e(throwable.message)
                }
    }

    fun getMovies(): MutableLiveData<List<DiscoveryViewItem>> {
        return movies
    }

    private fun modelToView(discoveryMovieModel: DiscoveryMovieModel): DiscoveryViewItem {
        return DiscoveryViewItem(discoveryMovieModel.id,
                discoveryMovieModel.title,
                discoveryMovieModel.posterPath,
                discoveryMovieModel.overview,
                discoveryMovieModel.voteAverage.toString())
    }

}
