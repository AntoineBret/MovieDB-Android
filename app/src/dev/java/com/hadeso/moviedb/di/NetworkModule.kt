package com.hadeso.moviedb.di

import com.hadeso.moviedb.BuildConfig
import com.hadeso.moviedb.api.MovieDBService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val TIMEOUT_SECONDS = 20

@Module
class NetworkModule {


    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        // set your desired log level
        if (BuildConfig.WITH_LOGS) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.NONE
        }

        return OkHttpClient.Builder()
                .connectTimeout(TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build()
    }

    @Provides
    @Singleton
    fun provideMovieDBService(okHttpClient: OkHttpClient, baseUrl: HttpUrl, moshi: Moshi): MovieDBService {
        return Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build()
                .create(MovieDBService::class.java)
    }

    @Singleton
    @Provides
    fun provideBaseUrl(): HttpUrl {
        return HttpUrl.parse(BuildConfig.GATEWAY_BASE_ENDPOINT)!!
    }
}