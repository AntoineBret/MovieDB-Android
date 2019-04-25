package com.hadeso.moviedb.ui.discovery

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.fitCenterTransform
import com.hadeso.moviedb.R
import com.hadeso.moviedb.utils.inflate
import kotlinx.android.synthetic.main.view_discovery_movie.view.*

class DiscoveryAdapter(val actions: OnMovieSelectedListener)
  : ListAdapter<DiscoveryViewItem, DiscoveryAdapter.DiscoveryViewHolder>(DiscoveryDiffUtilCallback()) {

  /**
   * For further details on ListAdapter, please look at:
   * https://developer.android.com/reference/android/support/v7/recyclerview/extensions/ListAdapter
   */

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoveryViewHolder {
    return DiscoveryViewHolder(parent)
  }

  override fun onBindViewHolder(holder: DiscoveryViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  interface OnMovieSelectedListener {
    fun onMovieSelected(movie: DiscoveryViewItem, sharedElement: ImageView)
  }

  inner class DiscoveryViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    parent.inflate(R.layout.view_discovery_movie)) {

    fun bind(viewItem: DiscoveryViewItem) {
      itemView.setOnClickListener { actions.onMovieSelected(viewItem, itemView.moviePoster) }
      itemView.movieTitle.text = viewItem.title
      itemView.movieOverview.text = viewItem.overview
      itemView.moviePoster.transitionName = viewItem.id.toString()
      Glide
        .with(itemView.context)
        .load("https://image.tmdb.org/t/p/w154" + viewItem.posterUrl)
        .apply(fitCenterTransform())
        .into(itemView.moviePoster)
    }
  }

  /**
   * For further details on DiffUtil, please look at:
   * https://developer.android.com/reference/android/support/v7/util/DiffUtil
   */
  class DiscoveryDiffUtilCallback : DiffUtil.ItemCallback<DiscoveryViewItem>() {
    override fun areItemsTheSame(oldItem: DiscoveryViewItem, newItem: DiscoveryViewItem): Boolean {
      return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DiscoveryViewItem, newItem: DiscoveryViewItem): Boolean {
      return oldItem.title == newItem.title
        && oldItem.posterUrl == newItem.posterUrl
    }
  }
}
