package com.hadeso.moviedb.di.module

import android.content.Context
import com.hadeso.moviedb.MovieDBApp
import com.hadeso.moviedb.architecture.Store
import com.hadeso.moviedb.core.state.AppState
import com.hadeso.moviedb.feature.discovery.detail.state.MovieDetailState
import com.hadeso.moviedb.feature.discovery.state.DiscoveryState
import com.hadeso.moviedb.feature.discovery.state.discoveryMutator
import com.hadeso.moviedb.feature.discovery.detail.state.movieDetailMutator
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {
    @JvmStatic
    @Provides
    @Singleton
    fun provideApplication(app: MovieDBApp): Context = app

    @JvmStatic
    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @JvmStatic
    @Provides
    @Singleton
    fun provideStore(): Store<AppState> {
        val initialState = AppState(
            discoveryState = DiscoveryState.Idle,
            movieDetailState = MovieDetailState.Idle
        )
        return Store(initialState).apply {
            register(DiscoveryState::class.java, discoveryMutator)
            register(MovieDetailState::class.java, movieDetailMutator)
        }
    }
}
