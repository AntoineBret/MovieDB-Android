package com.hadeso.moviedb.di

import com.hadeso.moviedb.ui.discovery.DiscoveryFragment
import com.hadeso.moviedb.ui.movie.MovieFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeDiscoveryFragment(): DiscoveryFragment

    @ContributesAndroidInjector
    abstract fun contributeMovieFragment(): MovieFragment
}