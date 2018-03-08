package com.hadeso.moviedb.di

import android.content.Context
import com.hadeso.moviedb.MovieDBApp
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by 77796 on 05-Mar-18.
 */
@Module
class AppModule {
    @Provides
    @Singleton
    fun provideApplication(app: MovieDBApp): Context = app

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
    }
}