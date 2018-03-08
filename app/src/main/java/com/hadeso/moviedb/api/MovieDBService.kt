package com.hadeso.moviedb.api

import com.hadeso.moviedb.model.DiscoveryModel
import io.reactivex.Single
import retrofit2.http.GET


/**
 * Created by 77796 on 07-Mar-18.
 */
const val API_KEY = "f161afbc1f127b633c7c59bdc5e7a6a0"
const val API_QUERY = "?api_key="

interface MovieDBService {

    @GET("discover/movie$API_QUERY$API_KEY")
    fun discover(): Single<DiscoveryModel>

}