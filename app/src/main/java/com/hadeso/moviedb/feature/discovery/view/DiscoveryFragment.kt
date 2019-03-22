package com.hadeso.moviedb.feature.discovery.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hadeso.moviedb.R
import com.hadeso.moviedb.di.Injectable
import com.hadeso.moviedb.feature.discovery.detail.view.MovieDetailFragment
import com.hadeso.moviedb.feature.discovery.state.DiscoveryState
import com.hadeso.moviedb.feature.discovery.view.adapter.DiscoveryAdapter
import com.hadeso.moviedb.utils.AutoClearedValue
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_discovery.discoveryRecyclerView
import kotlinx.android.synthetic.main.fragment_discovery.progressBar
import javax.inject.Inject
import com.hadeso.moviedb.architecture.base.View as BaseView

class DiscoveryFragment : Fragment(), Injectable, BaseView<DiscoveryIntent, DiscoveryState>, DiscoveryAdapter.OnMovieSelectedListener {

    companion object {
        fun newInstance(): Fragment {
            return DiscoveryFragment()
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: DiscoveryViewModel
    private lateinit var adapter: AutoClearedValue<DiscoveryAdapter>

    private val discoveryIntentSubject = PublishSubject.create<DiscoveryIntent>()

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
        }

        discoveryIntentSubject.onNext(DiscoveryIntent.Initial)
    }

    override fun intents(): Observable<DiscoveryIntent> = discoveryIntentSubject

    override fun render(state: DiscoveryState) {
        when (state) {
            is DiscoveryState.Loading -> showLoading(true)
            is DiscoveryState.MoviesLoaded -> {
                showLoading(false)
                updateList(state.data.discoveryMovies)
            }
            is DiscoveryState.MovieNavigation -> goToMovie(state.movieId)
        }
    }

    override fun onMovieSelected(movie: DiscoveryViewItem, sharedElement: ImageView) {
        discoveryIntentSubject.onNext(DiscoveryIntent.MovieSelected(movie.id))
    }

    private fun goToMovie(movieId: Int) {
        val nextFragment = MovieDetailFragment.newInstance(movieId)

        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction//?.addSharedElement(sharedElement, sharedElement.transitionName)
            ?.replace(R.id.container, nextFragment)
            ?.addToBackStack(null)
            ?.commit()
    }

    private fun updateList(items: List<DiscoveryViewItem>) {
        if (!this::adapter.isInitialized || adapter.get() == null) {
            initRecyclerView()
        }
        adapter.get()?.submitList(items)
    }

    private fun initRecyclerView() {
        val discoveryAdapter = DiscoveryAdapter(this)
        adapter = AutoClearedValue(this, discoveryAdapter)
        discoveryRecyclerView.adapter = discoveryAdapter
        discoveryRecyclerView.apply {
            setHasFixedSize(true)
            val linearLayout = LinearLayoutManager(context)
            layoutManager = linearLayout
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) progressBar.visibility = android.view.View.VISIBLE else progressBar.visibility = android.view.View.GONE
    }
}
