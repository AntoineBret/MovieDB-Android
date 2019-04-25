package com.hadeso.moviedb.di.fragmentModule

import com.hadeso.moviedb.ui.discovery.DiscoveryFragment
import com.hadeso.moviedb.ui.home.HomeFragment
import com.hadeso.moviedb.ui.movie.MovieFragment
import com.hadeso.moviedb.ui.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

  /**
   * Home
   */
  @ContributesAndroidInjector
  abstract fun contributeHomeFragment(): HomeFragment

  /**
   * Discovery
   */
  @ContributesAndroidInjector
  abstract fun contributeDiscoveryFragment(): DiscoveryFragment

  @ContributesAndroidInjector
  abstract fun contributeMovieFragment(): MovieFragment

  /**
   * Search
   */
  @ContributesAndroidInjector
  abstract fun contributeSearchFragment(): SearchFragment
}
