package com.hadeso.moviedb.feature.discovery.view

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.fitCenterTransform
import com.hadeso.moviedb.R
import com.hadeso.moviedb.utils.inflate
import kotlinx.android.synthetic.main.view_discovery_movie.view.movieOverview
import kotlinx.android.synthetic.main.view_discovery_movie.view.moviePoster
import kotlinx.android.synthetic.main.view_discovery_movie.view.movieTitle

class DiscoveryAdapter(
    private var discoveryViewItems: List<DiscoveryViewItem>,
    val actions: OnMovieSelectedListener
) : RecyclerView.Adapter<DiscoveryAdapter.DiscoveryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoveryViewHolder {
        return DiscoveryViewHolder(parent)
    }

    override fun onBindViewHolder(holder: DiscoveryViewHolder, position: Int) {
        val item = discoveryViewItems[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return discoveryViewItems.size
    }

    fun updateMovies(discoveryViewItems: List<DiscoveryViewItem>) {
        this.discoveryViewItems = discoveryViewItems
    }

    interface OnMovieSelectedListener {
        fun onMovieSelected(movie: DiscoveryViewItem, sharedElement: ImageView)
    }

    inner class DiscoveryViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.view_discovery_movie)
    ) {

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
}
