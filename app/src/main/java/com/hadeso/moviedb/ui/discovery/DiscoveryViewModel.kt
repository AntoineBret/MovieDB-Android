package com.hadeso.moviedb.ui.discovery

import android.arch.lifecycle.ViewModel
import com.hadeso.moviedb.repository.MovieRepository
import javax.inject.Inject

/**
 * Created by 77796 on 21-Dec-17.
 */
class DiscoveryViewModel @Inject constructor(val movieRepository: MovieRepository) : ViewModel() {

    fun init() {
        movieRepository.getDiscoveryMovies()
    }
}