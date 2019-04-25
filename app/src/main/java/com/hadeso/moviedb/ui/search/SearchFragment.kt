package com.hadeso.moviedb.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.hadeso.moviedb.R
import com.hadeso.moviedb.di.archModule.Injectable
import com.hadeso.moviedb.ui.sortBy.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.*
import java.util.stream.Collectors
import java.util.stream.IntStream
import javax.inject.Inject

@SuppressLint("ValidFragment")
class SearchFragment(val sortByListener: SortByListener, val searchFragmentListener: SearchFragmentListener) : DialogFragment(), Injectable {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  private lateinit var viewModel: SearchViewModel

  private var expandableListView: ExpandableListView? = null
  private var expandableListAdapter: ExpandableListAdapter? = null

  var expandableListTitle: List<String>? = null
  var expandableListDetail: HashMap<String, List<Any>>? = null

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_search, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)

    loadData()
    initRecyclerView()
    initListeners()

    dismiss_button.setOnClickListener {
      dismiss()
    }
  }

  private fun loadData() {
    viewModel.getGenres().observe(this, Observer<List<GenreViewItem>> { genres ->
      genres?.let {
        viewModel.genres.addAll(it.map { it.name })
      }
    })

    viewModel.sortBy.add("Popularité")
    viewModel.sortBy.add("Note")
    viewModel.sortBy.add("Date de sortie")
    viewModel.sortBy.add("Titre")

    val range = IntStream.rangeClosed(1900, 2019).boxed().sorted(Collections.reverseOrder()).collect(Collectors.toList())
    viewModel.year.addAll(range)
  }

  private fun initRecyclerView() {
    expandableListView = view!!.findViewById(R.id.search_lv) as ExpandableListView

    expandableListDetail = viewModel.data
    expandableListTitle = ArrayList<String>(expandableListDetail!!.keys)

    expandableListAdapter = expandableListTitle?.let { context?.let { it1 -> SearchAdapter(it1, it, expandableListDetail!!) } }
    expandableListView!!.setAdapter(expandableListAdapter)

  }

  private fun initListeners() {
    expandableListView!!.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
      sortByListener.onSearchSelected(true)
      when (groupPosition) {
        0 -> searchFragmentListener.onTypeSelected(expandableListDetail!![expandableListTitle!![groupPosition]]!![childPosition] as String)
        1 -> searchFragmentListener.onYearSelected(expandableListDetail!![expandableListTitle!![groupPosition]]!![childPosition] as Int)
        2 -> getGenreIdByName(expandableListDetail!![expandableListTitle!![groupPosition]]!![childPosition] as String)
      }
      false
    }
  }

  private fun getGenreIdByName(data: String) {
    //todo : Check if there is a better way for achieve logic
    viewModel.getGenres().observe(this, Observer<List<GenreViewItem>> { genres ->
      genres?.let {
        val filtered = it!!.filter { it.name.equals(data)}
        filtered.forEach {
          searchFragmentListener.onGenreSelected(it.id)
        }
      }
    })
  }
}
