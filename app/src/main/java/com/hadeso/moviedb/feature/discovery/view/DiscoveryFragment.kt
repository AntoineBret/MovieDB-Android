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
import com.hadeso.moviedb.feature.discovery.detail.view.MovieFragment
import com.hadeso.moviedb.utils.AutoClearedValue
import kotlinx.android.synthetic.main.fragment_discovery.discoveryRecyclerView
import javax.inject.Inject

class DiscoveryFragment : Fragment(), Injectable, DiscoveryAdapter.OnMovieSelectedListener {

    companion object {
        fun newInstance(): Fragment {
            return DiscoveryFragment()
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: DiscoveryViewModel

    private lateinit var adapter: AutoClearedValue<DiscoveryAdapter>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_discovery, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DiscoveryViewModel::class.java)

        initRecyclerView(emptyList())
        initLiveData()
    }

    override fun onMovieSelected(movie: DiscoveryViewItem, sharedElement: ImageView) {


        val nextFragment = MovieFragment.newInstance(movie)

        val fragmentTransaction = fragmentManager?.beginTransaction()


        fragmentTransaction?.addSharedElement(sharedElement, sharedElement.transitionName)
                ?.replace(R.id.container, nextFragment)
                ?.addToBackStack(null)
                ?.commit()
    }

    private fun initLiveData() {
        viewModel.getMovies().observe(this, Observer<List<DiscoveryViewItem>> { movies ->
            updateMovies(movies!!)
        })
    }

    private fun initRecyclerView(movies: List<DiscoveryViewItem>) {
        val movieAdapter = DiscoveryAdapter(movies, this)
        discoveryRecyclerView.adapter = movieAdapter
        discoveryRecyclerView.apply {
            setHasFixedSize(true)
            val linearLayout = LinearLayoutManager(context)
            layoutManager = linearLayout
        }
        adapter = AutoClearedValue(this, movieAdapter)
    }

    private fun updateMovies(movies: List<DiscoveryViewItem>) {
        adapter.get()?.updateMovies(movies)
        adapter.get()?.notifyDataSetChanged()
    }

}
