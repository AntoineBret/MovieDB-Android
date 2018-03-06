package com.hadeso.moviedb.di

import com.hadeso.moviedb.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by 77796 on 05-Mar-18.
 */
@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = arrayOf(FragmentBuildersModule::class))
    abstract fun contributeMainActivity(): MainActivity
}