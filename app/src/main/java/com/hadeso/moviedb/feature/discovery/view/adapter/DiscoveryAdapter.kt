package com.hadeso.moviedb.feature.discovery.view.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.fitCenterTransform
import com.hadeso.moviedb.R
import com.hadeso.moviedb.feature.discovery.view.DiscoveryViewItem
import com.hadeso.moviedb.utils.inflate
import kotlinx.android.synthetic.main.view_discovery_movie.view.movieOverview
import kotlinx.android.synthetic.main.view_discovery_movie.view.moviePoster
import kotlinx.android.synthetic.main.view_discovery_movie.view.movieTitle

class DiscoveryAdapter(val actions: OnMovieSelectedListener) :
    ListAdapter<DiscoveryViewItem, DiscoveryAdapter.DiscoveryViewHolder>(DiscoveryDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoveryViewHolder {
        return DiscoveryViewHolder(parent)
    }

    override fun onBindViewHolder(holder: DiscoveryViewHolder, position: Int) = holder.bind(getItem(position))

    interface OnMovieSelectedListener {
        fun onMovieSelected(movie: DiscoveryViewItem, sharedElement: ImageView)
    }

    inner class DiscoveryViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.view_discovery_movie)
    ) {

        init {
            itemView.setOnClickListener {
                actions.onMovieSelected(getItem(adapterPosition), itemView.moviePoster)
            }
        }

        fun bind(viewItem: DiscoveryViewItem) {
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
