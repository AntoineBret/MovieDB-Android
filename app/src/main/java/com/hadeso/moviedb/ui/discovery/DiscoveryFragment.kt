package com.hadeso.moviedb.ui.discovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hadeso.moviedb.R
import com.hadeso.moviedb.di.archModule.Injectable
import com.hadeso.moviedb.ui.movie.MovieFragment
import com.hadeso.moviedb.ui.search.SearchFragment
import com.hadeso.moviedb.ui.search.SortByListener
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_discovery.*
import javax.inject.Inject

class DiscoveryFragment : Fragment(), Injectable, DiscoveryAdapter.OnMovieSelectedListener, SortByListener {

  companion object {
    fun newInstance(): Fragment {
      return DiscoveryFragment()
    }
  }

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  private lateinit var viewModel: DiscoveryViewModel
  private val disposable = CompositeDisposable()
  private lateinit var adapter: DiscoveryAdapter

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_discovery, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProviders.of(this, viewModelFactory).get(DiscoveryViewModel::class.java)

    initHeader()
    initLiveData()
    initSearchBar()
    initSearchOptions()
  }

  override fun onSearchSelected(isSelected: Boolean) {
    if (isSelected){
      toolbarDiscovery.title = "pouet"
      menu_sort.setImageDrawable(resources.getDrawable(R.drawable.ic_cancel))
      viewModel.isSearchIsSelected.value = true
    }
  }

  override fun onMovieSelected(movie: DiscoveryViewItem, sharedElement: ImageView) {
    val nextFragment = MovieFragment.newInstance(movie)
    val fragmentTransaction = fragmentManager?.beginTransaction()

    fragmentTransaction?.addSharedElement(sharedElement, sharedElement.transitionName)
      ?.replace(R.id.discovery_container, nextFragment)
      ?.addToBackStack(null)
      ?.commit()
  }

  private fun initLiveData() {
    viewModel.getMovies().observe(this, Observer<List<DiscoveryViewItem>> { movies ->
      movies?.let {
        initRecyclerView(it)
      }
    })
  }

  private fun initRecyclerView(movies: List<DiscoveryViewItem>) {
    if (!this::adapter.isInitialized) {
      adapter = DiscoveryAdapter(this)
      discoveryRecyclerView.adapter = adapter
      discoveryRecyclerView.apply {
        setHasFixedSize(true)
        val linearLayout = LinearLayoutManager(context)
        layoutManager = linearLayout
      }
    }
    adapter.submitList(movies)
  }

  private fun initSearchBar() {
    disposable.add(RxSearchView.queryTextChanges(search_bar)
      .skipInitialValue()
      .subscribe { query -> viewModel.searchByQuery(query) })
  }

  private fun initHeader() {
    (activity as AppCompatActivity).setSupportActionBar(toolbarDiscovery)
    (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    toolbarDiscovery.title = getString(R.string.discover_movie_toolbar_title)

    Glide
      .with(this)
      .load("https://image.tmdb.org/t/p/w500_and_h282_face/qsD5OHqW7DSnaQ2afwz8Ptht1Xb.jpg")
      .apply(RequestOptions.fitCenterTransform())
      .into(backdrop)
  }

  private fun initSearchOptions() {
    menu_sort.setOnClickListener {
      if (viewModel.isSearchIsSelected.value == true){
        toolbarDiscovery.title = getString(R.string.discover_movie_toolbar_title)
        menu_sort.setImageDrawable(resources.getDrawable(R.drawable.ic_order_by))
        //todo : return to original list
        viewModel.isSearchIsSelected.value = false
      }else{
        val fm = fragmentManager
        val dialogFragment = SearchFragment(this, viewModel)
        dialogFragment.show(fm!!, "Sample Fragment")
      }
    }
  }
}
