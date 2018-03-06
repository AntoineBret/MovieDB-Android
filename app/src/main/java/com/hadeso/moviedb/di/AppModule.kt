package com.hadeso.moviedb.di

import android.content.Context
import com.hadeso.moviedb.MovieDBApp
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
}