package com.hadeso.moviedb.di.activityModule

import com.hadeso.moviedb.ui.HomeActivity
import com.hadeso.moviedb.di.fragmentModule.FragmentBuildersModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = arrayOf(FragmentBuildersModule::class))
    abstract fun contributeHomeActivity(): HomeActivity
}
