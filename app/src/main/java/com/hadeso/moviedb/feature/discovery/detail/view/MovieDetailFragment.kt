package com.hadeso.moviedb.feature.discovery.detail.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.hadeso.moviedb.R
import com.hadeso.moviedb.di.Injectable
import com.hadeso.moviedb.feature.discovery.detail.state.MovieDetailState
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_movie_detail.movieBackdrop
import kotlinx.android.synthetic.main.fragment_movie_detail.movieDescription
import kotlinx.android.synthetic.main.fragment_movie_detail.moviePoster
import kotlinx.android.synthetic.main.fragment_movie_detail.movieTitleTextView
import kotlinx.android.synthetic.main.fragment_movie_detail.progressBar
import javax.inject.Inject
import com.bumptech.glide.request.target.Target as GlideTarget
import com.hadeso.moviedb.architecture.base.View as BaseView

class MovieDetailFragment : Fragment(), Injectable, BaseView<MovieDetailIntent, MovieDetailState> {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: MovieDetailViewModel

    private val args: MovieDetailFragmentArgs by navArgs()
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

        val movieId = args.movieId
        setupTransition(movieId.toString(), args.posterUrl, args.movieTitle)
        movieDetailIntentSubject.onNext(MovieDetailIntent.Initial(movieId))
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

    private fun setupTransition(movieId: String, posterUrl: String, movieTitle: String) {
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        moviePoster.transitionName = movieId
        movieTitleTextView.text = movieTitle
        movieTitleTextView.transitionName = movieTitle
        Glide.with(requireContext())
            .load("https://image.tmdb.org/t/p/w154$posterUrl")
            .apply(RequestOptions.fitCenterTransform())
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: GlideTarget<Drawable>?, isFirstResource: Boolean): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: GlideTarget<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }
            })
            .into(object : CustomTarget<Drawable>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                }

                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    moviePoster.background = resource
                }
            })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) progressBar.visibility = android.view.View.VISIBLE else progressBar.visibility = android.view.View.GONE
    }

    private fun showMovieDetail(movieDetailViewItem: MovieDetailViewItem?) {
        movieDescription.text = movieDetailViewItem?.overview
        context?.let {
            Glide.with(it)
                .load("https://image.tmdb.org/t/p/w780" + movieDetailViewItem?.backdropPath)
                .into(movieBackdrop)
        }
    }
}
