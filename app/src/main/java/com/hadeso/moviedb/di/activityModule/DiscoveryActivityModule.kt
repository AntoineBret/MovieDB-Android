package com.hadeso.moviedb.di.activityModule

import com.hadeso.moviedb.di.fragmentModule.FragmentBuildersModule
import com.hadeso.moviedb.ui.DiscoveryActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class DiscoveryActivityModule {
  @ContributesAndroidInjector(modules = arrayOf(FragmentBuildersModule::class))
  abstract fun contributeDiscoveryActivity(): DiscoveryActivity
}
