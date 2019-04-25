package com.hadeso.moviedb.ui.discovery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hadeso.moviedb.model.DiscoveryMovieModel
import com.hadeso.moviedb.repository.MovieRepository
import com.hadeso.moviedb.ui.search.SearchFragmentListener
import com.hadeso.moviedb.utils.DateFormat.getDateFormatted
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class DiscoveryViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel(), SearchFragmentListener {

  private val disposable = CompositeDisposable()

  private val originalMovieList: MutableLiveData<List<DiscoveryViewItem>> = MutableLiveData()
  private var queryViewItems: List<DiscoveryViewItem> = listOf()

  val isSearchIsSelected: MutableLiveData<Boolean> = MutableLiveData()

  init {
    getDiscoveryMovies()
  }

  /**
   * Movie
   */
  private fun getDiscoveryMovies() {
    disposable.add(movieRepository.getDiscoveryMovies()
      .map { model -> movieModelToView(model) }
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

  private fun movieModelToView(discoveryMovieModel: DiscoveryMovieModel): DiscoveryViewItem {
    return DiscoveryViewItem(discoveryMovieModel.id,
      discoveryMovieModel.title,
      discoveryMovieModel.posterPath,
      discoveryMovieModel.overview,
      discoveryMovieModel.voteAverage.toString(),
      discoveryMovieModel.genre,
      discoveryMovieModel.releaseDate,
      discoveryMovieModel.popularity)
  }

  /**
   * Order list by selected type
   */
  override fun onTypeSelected(sortBy: String) {
    disposable.add(
      Single.fromCallable {
        return@fromCallable when (sortBy) {
          "Popularité" -> queryViewItems.sortedBy {
            it.popularity
          }
          "Note" -> queryViewItems.sortedBy {
            it.voteAverage
          }
          "Date de sortie" -> queryViewItems.sortedBy {
            it.release_date
          }
          "Titre" -> queryViewItems.sortedBy {
            it.title
          }
          else -> queryViewItems
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

  /**
   * Select movie by year
   */
  override fun onYearSelected(year: Int) {
    disposable.add(
      Single.fromCallable {
        return@fromCallable queryViewItems.filter {
          getDateFormatted(it.release_date).contains(year.toString(), true)
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

  /**
   * Select movie by genre
   */
  override fun onGenreSelected(genre: Int) {
    disposable.add(
      Single.fromCallable {
        return@fromCallable queryViewItems.filter {
          it.genreId.contains(genre)
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

  /**
   * Search a movie (searchbar)
   */
  fun searchByQuery(query: CharSequence) {
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

  fun displayFullList() {
    disposable.add(
      Single.fromCallable {
        return@fromCallable queryViewItems
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
