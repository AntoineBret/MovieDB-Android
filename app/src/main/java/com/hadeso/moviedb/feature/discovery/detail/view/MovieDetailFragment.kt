package com.hadeso.moviedb.feature.discovery.detail.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hadeso.moviedb.R
import com.hadeso.moviedb.di.Injectable
import com.hadeso.moviedb.feature.discovery.detail.state.MovieDetailState
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_movie_detail.movieBackdrop
import kotlinx.android.synthetic.main.fragment_movie_detail.movieDescription
import kotlinx.android.synthetic.main.fragment_movie_detail.moviePoster
import kotlinx.android.synthetic.main.fragment_movie_detail.movieTitle
import kotlinx.android.synthetic.main.fragment_movie_detail.progressBar
import javax.inject.Inject
import com.hadeso.moviedb.architecture.base.View as BaseView


const val KEY_MOVIE_ID = "MOVIE_ID"

class MovieDetailFragment : Fragment(), Injectable, BaseView<MovieDetailIntent, MovieDetailState> {

    companion object {
        fun newInstance(movieId: Int): Fragment {
            val bundle = Bundle()
            val fragment = MovieDetailFragment()
            bundle.putInt(KEY_MOVIE_ID, movieId)
            fragment.arguments = bundle
            return fragment
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: MovieDetailViewModel

    private val movieDetailIntentSubject = PublishSubject.create<MovieDetailIntent>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieDetailViewModel::class.java)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieDetailViewModel::class.java).apply {
            processIntents(intents())
            stateLiveData().observe(this@MovieDetailFragment, Observer<MovieDetailState> { state ->
                render(state)
            })
        }

        val movieId: Int? = arguments?.getInt(KEY_MOVIE_ID)
        movieDetailIntentSubject.onNext(MovieDetailIntent.Initial(movieId!!))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun intents(): Observable<MovieDetailIntent> {
        return movieDetailIntentSubject
    }

    override fun render(state: MovieDetailState) {
        when (state) {
            is MovieDetailState.Loading -> showLoading(true)
            is MovieDetailState.DetailsLoaded -> {
                showLoading(false)
                showMovieDetail(state.data.movieDetailViewItem)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) progressBar.visibility = android.view.View.VISIBLE else progressBar.visibility = android.view.View.GONE
    }

    private fun showMovieDetail(movieDetailViewItem: MovieDetailViewItem?) {
        moviePoster.transitionName = movieDetailViewItem?.id.toString()
        movieTitle.text = movieDetailViewItem?.title
        movieDescription.text = movieDetailViewItem?.overview
        context?.let {
            Glide.with(it)
                .load("https://image.tmdb.org/t/p/w154" + movieDetailViewItem?.posterPath)
                .apply(RequestOptions.fitCenterTransform())
                .into(moviePoster)
            Glide.with(it)
                .load("https://image.tmdb.org/t/p/w780" + movieDetailViewItem?.backdropPath)
                .into(movieBackdrop)
        }
    }
}
