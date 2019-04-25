package com.hadeso.moviedb.ui.search

interface SearchFragmentListener {
  fun onTypeSelected(sortBy: String)
  fun onYearSelected(year: Int)
  fun onGenreSelected(genre: Int)
}

interface SortByListener {
  fun onSearchSelected(isSelected: Boolean, selectedValue: String)
}
