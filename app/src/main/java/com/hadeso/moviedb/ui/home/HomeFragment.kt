package com.hadeso.moviedb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.hadeso.moviedb.R
import com.hadeso.moviedb.di.archModule.Injectable
import javax.inject.Inject

class HomeFragment : Fragment(), Injectable {

  companion object {
    fun newInstance(): Fragment {
      return HomeFragment()
    }
  }

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  private lateinit var viewModel: HomeViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_home, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)

    initToolbar()
  }

  private fun initToolbar() {
    (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.home_fragment_toolbar_title)
  }
}
