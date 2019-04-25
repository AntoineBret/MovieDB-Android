package com.hadeso.moviedb.di.viewmodelModule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hadeso.moviedb.di.archModule.ViewModelKey
import com.hadeso.moviedb.ui.discovery.DiscoveryViewModel
import com.hadeso.moviedb.ui.home.HomeViewModel
import com.hadeso.moviedb.ui.movie.MovieViewModel
import com.hadeso.moviedb.ui.sortBy.SearchViewModel
import com.hadeso.moviedb.viewmodel.MovieDBViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

  /**
   * Home
   */
  @Binds
  @IntoMap
  @ViewModelKey(HomeViewModel::class)
  abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

  /**
   * Discovery
   */
  @Binds
  @IntoMap
  @ViewModelKey(DiscoveryViewModel::class)
  abstract fun bindDiscoveryViewModel(discoveryViewModel: DiscoveryViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(MovieViewModel::class)
  abstract fun bindMovieViewModel(movieViewModel: MovieViewModel): ViewModel

  /**
   * Search dialog
   */
  @Binds
  @IntoMap
  @ViewModelKey(SearchViewModel::class)
  abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel

  @Binds
  abstract fun bindViewModelFactory(factory: MovieDBViewModelFactory): ViewModelProvider.Factory

}
