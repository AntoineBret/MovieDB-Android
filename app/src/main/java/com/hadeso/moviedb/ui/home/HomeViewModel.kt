package com.hadeso.moviedb.ui.home

import androidx.lifecycle.ViewModel
import com.hadeso.moviedb.repository.MovieRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {


}
