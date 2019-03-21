package com.hadeso.moviedb.di.module

import com.hadeso.moviedb.feature.discovery.view.DiscoveryFragment
import com.hadeso.moviedb.feature.discovery.detail.view.MovieFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeDiscoveryFragment(): DiscoveryFragment

    @ContributesAndroidInjector
    abstract fun contributeMovieFragment(): MovieFragment
}
