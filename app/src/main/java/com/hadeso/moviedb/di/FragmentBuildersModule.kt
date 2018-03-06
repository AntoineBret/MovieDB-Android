package com.hadeso.moviedb.di

import com.hadeso.moviedb.ui.discovery.DiscoveryFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by 77796 on 05-Mar-18.
 */
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeDiscoveryFragment(): DiscoveryFragment
}