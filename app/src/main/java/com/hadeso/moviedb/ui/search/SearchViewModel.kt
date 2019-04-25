package com.hadeso.moviedb.ui.sortBy

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hadeso.moviedb.model.GenresModel
import com.hadeso.moviedb.repository.MovieRepository
import com.hadeso.moviedb.ui.search.GenreViewItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {
  private val disposable = CompositeDisposable()

  private val genreList: MutableLiveData<List<GenreViewItem>> = MutableLiveData()
  private var genreListItems: List<GenreViewItem> = listOf()

  val genres = ArrayList<String>()
  val sortBy = ArrayList<String>()
  val year = ArrayList<Int>()

  init {
    disposable.add(movieRepository.getGenre()
      .map { model -> modelToView(model) }
      .toList()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe({ views ->
        genreList.value = views
        genreListItems = views
      }) { throwable ->
        Timber.e(throwable.message)
      })
  }

  fun getGenres(): MutableLiveData<List<GenreViewItem>> {
    return genreList
  }

  private fun modelToView(genresModel: GenresModel): GenreViewItem {
    return GenreViewItem(
      genresModel.id,
      genresModel.name)
  }

  val data: java.util.HashMap<String, List<Any>>
    get() {
      val expandableListDetail = java.util.HashMap<String, List<Any>>()

      expandableListDetail["Genres"] = genres
      expandableListDetail["Trier par"] = sortBy
      expandableListDetail["Année"] = year

      return expandableListDetail
    }
}
