package com.hadeso.moviedb.di.archModule

import android.app.Application
import com.hadeso.moviedb.MovieDBApp
import com.hadeso.moviedb.di.NetworkModule
import com.hadeso.moviedb.di.activityModule.DiscoveryActivityModule
import com.hadeso.moviedb.di.activityModule.MainActivityModule
import com.hadeso.moviedb.di.viewmodelModule.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
  AndroidInjectionModule::class,
  AppModule::class,
  NetworkModule::class,
  ViewModelModule::class,
  MainActivityModule::class,
  DiscoveryActivityModule::class))
interface AppComponent {
  @Component.Builder
  interface Builder {
    @BindsInstance
    fun application(application: Application): Builder

    fun build(): AppComponent
  }

  fun inject(movieDBApp: MovieDBApp)
}
