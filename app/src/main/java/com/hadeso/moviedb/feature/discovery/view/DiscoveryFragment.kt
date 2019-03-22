package com.hadeso.moviedb.feature.discovery.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hadeso.moviedb.R
import com.hadeso.moviedb.di.Injectable
import com.hadeso.moviedb.feature.discovery.state.DiscoveryState
import com.hadeso.moviedb.feature.discovery.view.adapter.DiscoveryAdapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_discovery.discoveryRecyclerView
import kotlinx.android.synthetic.main.fragment_discovery.progressBar
import javax.inject.Inject
import com.hadeso.moviedb.architecture.base.View as BaseView

class DiscoveryFragment : Fragment(), Injectable, BaseView<DiscoveryIntent, DiscoveryState>, DiscoveryAdapter.OnMovieSelectedListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: DiscoveryViewModel
    private lateinit var adapter: DiscoveryAdapter

    private val discoveryIntentSubject = PublishSubject.create<DiscoveryIntent>()

    private lateinit var pendingTransitionImageView: ImageView
    private lateinit var pendingTransitionTextView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_discovery, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (!this::viewModel.isInitialized) {
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(DiscoveryViewModel::class.java).apply {
                processIntents(intents())
                stateLiveData().observe(this@DiscoveryFragment, Observer<DiscoveryState> { state ->
                    render(state)
                })
            }
            discoveryIntentSubject.onNext(DiscoveryIntent.Initial)
        }

        if (this::adapter.isInitialized) {
            initRecyclerView(adapter)
        }
    }

    override fun intents(): Observable<DiscoveryIntent> = discoveryIntentSubject

    override fun render(state: DiscoveryState) {
        when (state) {
            is DiscoveryState.Loading -> showLoading(true)
            is DiscoveryState.MoviesLoaded -> {
                showLoading(false)
                updateList(state.data.discoveryMovies)
            }
            is DiscoveryState.MovieNavigation -> goToMovie(state.movieId, state.posterUrl, state.movieTitle)
        }
    }

    override fun onMovieSelected(movie: DiscoveryViewItem, posterView: ImageView, titleView: TextView) {
        pendingTransitionImageView = posterView
        pendingTransitionTextView = titleView
        discoveryIntentSubject.onNext(DiscoveryIntent.MovieSelected(movie.id, movie.posterUrl, movie.title))
    }

    private fun goToMovie(movieId: Int, posterUrl: String, movieTitle: String) {
        val extras = FragmentNavigatorExtras(
            pendingTransitionImageView to movieId.toString(),
            pendingTransitionTextView to movieTitle
        )
        val action = DiscoveryFragmentDirections.actionDiscoveryFragmentToMovieDetailFragment(movieId, posterUrl, movieTitle)
        findNavController().navigate(action, extras)
    }

    private fun updateList(items: List<DiscoveryViewItem>) {
        if (discoveryRecyclerView.adapter == null) {
            if (!this::adapter.isInitialized) {
                adapter = DiscoveryAdapter(this)
            }
            initRecyclerView(adapter)
        }
        adapter.submitList(items)
    }

    private fun initRecyclerView(adapter: DiscoveryAdapter) {
        postponeEnterTransition()
        discoveryRecyclerView.adapter = adapter
        discoveryRecyclerView.apply {
            setHasFixedSize(true)
            val linearLayout = LinearLayoutManager(context)
            layoutManager = linearLayout
        }
        discoveryRecyclerView.post {
            startPostponedEnterTransition()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) progressBar.visibility = android.view.View.VISIBLE else progressBar.visibility = android.view.View.GONE
    }
}
