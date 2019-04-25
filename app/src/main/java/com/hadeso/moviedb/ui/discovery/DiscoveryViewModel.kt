package com.hadeso.moviedb.ui.discovery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hadeso.moviedb.model.DiscoveryMovieModel
import com.hadeso.moviedb.repository.MovieRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class DiscoveryViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

  private val disposable = CompositeDisposable()
  private val originalMovieList: MutableLiveData<List<DiscoveryViewItem>> = MutableLiveData()
  private var queryViewItems: List<DiscoveryViewItem> = listOf()

  init {
    disposable.add(movieRepository.getDiscoveryMovies()
      .map { model -> modelToView(model) }
      .toList()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe({ views ->
        originalMovieList.value = views
        queryViewItems = views
      }) { throwable ->
        Timber.e(throwable.message)
      })
  }

  fun getMovies(): MutableLiveData<List<DiscoveryViewItem>> {
        return originalMovieList
    }

    private fun modelToView(discoveryMovieModel: DiscoveryMovieModel): DiscoveryViewItem {
        return DiscoveryViewItem(discoveryMovieModel.id,
                discoveryMovieModel.title,
                discoveryMovieModel.posterPath,
                discoveryMovieModel.overview,
                discoveryMovieModel.voteAverage.toString())
    }

  fun search(query: CharSequence) {
    disposable.add(
      Single.fromCallable {
        return@fromCallable if (query.isNotEmpty()) {
          queryViewItems.filter {
            it.title.contains(query, true)
          }
        } else {
          queryViewItems
        }
      }
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ views ->
          originalMovieList.value = views
        }) { throwable ->
          Timber.e(throwable.message)
        }
    )
  }
}
