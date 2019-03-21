package com.hadeso.moviedb.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hadeso.moviedb.di.ViewModelKey
import com.hadeso.moviedb.feature.discovery.view.DiscoveryViewModel
import com.hadeso.moviedb.feature.discovery.detail.view.MovieDetailViewModel
import com.hadeso.moviedb.core.view.MovieDBViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(DiscoveryViewModel::class)
    abstract fun bindDiscoveryViewModel(discoveryViewModel: DiscoveryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel::class)
    abstract fun bindMovieViewModel(movieViewModel: MovieDetailViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: MovieDBViewModelFactory): ViewModelProvider.Factory

}
