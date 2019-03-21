package com.hadeso.moviedb.ui.movie

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.hadeso.moviedb.R
import com.hadeso.moviedb.di.Injectable
import com.hadeso.moviedb.model.MovieModel
import com.hadeso.moviedb.ui.discovery.DiscoveryViewItem
import kotlinx.android.synthetic.main.fragment_movie.movieBackdrop
import kotlinx.android.synthetic.main.fragment_movie.movieDescription
import kotlinx.android.synthetic.main.fragment_movie.moviePoster
import kotlinx.android.synthetic.main.fragment_movie.movieTitle
import javax.inject.Inject

const val KEY_MOVIE = "MOVIE"

class MovieFragment : Fragment(), Injectable {

    companion object {
        fun newInstance(movie: DiscoveryViewItem): Fragment {
            val bundle = Bundle()
            val fragment = MovieFragment()
            bundle.putParcelable(KEY_MOVIE, movie)
            fragment.arguments = bundle
            return fragment
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context)
                .inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieViewModel::class.java)

        val movie: DiscoveryViewItem? = arguments?.getParcelable(KEY_MOVIE)

        moviePoster.transitionName = movie!!.id.toString()
        movieTitle.text = movie.title
        movieDescription.text = movie.overview
        Glide.with(context!!)
                .load("https://image.tmdb.org/t/p/w154" + movie.posterUrl)
                .apply(RequestOptions.fitCenterTransform())
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        startPostponedEnterTransition()
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        startPostponedEnterTransition()
                        return false
                    }
                })
                .into(moviePoster)
        viewModel.init(movie)
        initLiveData()

    }

    private fun initLiveData() {
        viewModel.getMovie().observe(this, Observer<MovieModel> { movie ->
            Glide.with(context!!)
                    .load("https://image.tmdb.org/t/p/w780" + movie!!.backdropPath)
                    .into(movieBackdrop)
        })
    }

}
