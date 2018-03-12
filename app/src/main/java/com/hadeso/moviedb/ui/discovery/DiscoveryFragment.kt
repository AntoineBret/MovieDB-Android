package com.hadeso.moviedb.ui.discovery

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hadeso.moviedb.R
import com.hadeso.moviedb.di.Injectable
import com.hadeso.moviedb.utils.AutoClearedValue
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

/**
 * Created by 77796 on 21-Dec-17.
 */
class DiscoveryFragment : Fragment(), Injectable {

    companion object {
        fun newInstance(): Fragment {
            return DiscoveryFragment()
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: DiscoveryViewModel

    private lateinit var adapter: AutoClearedValue<DiscoveryAdapter>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DiscoveryViewModel::class.java)
        viewModel.init()

        initRecyclerView(emptyList())
        initLiveData()
    }

    private fun initLiveData() {
        viewModel.getMovies().observe(this, Observer<List<DiscoveryViewItem>> { movies ->
            updateMovies(movies!!)
        })
    }

    private fun initRecyclerView(movies: List<DiscoveryViewItem>) {
        val movieAdapter = DiscoveryAdapter(movies)
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