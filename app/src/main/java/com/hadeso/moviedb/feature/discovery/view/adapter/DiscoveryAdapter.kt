package com.hadeso.moviedb.feature.discovery.view.adapter

import android.graphics.drawable.Drawable
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.fitCenterTransform
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
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
        fun onMovieSelected(movie: DiscoveryViewItem, posterView: ImageView, titleView: TextView)
    }

    inner class DiscoveryViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.view_discovery_movie)
    ) {

        init {
            itemView.setOnClickListener {
                actions.onMovieSelected(getItem(adapterPosition), itemView.moviePoster, itemView.movieTitle)
            }
        }

        fun bind(viewItem: DiscoveryViewItem) {
            itemView.movieTitle.text = viewItem.title
            itemView.movieTitle.transitionName = viewItem.title
            itemView.movieOverview.text = viewItem.overview
            itemView.moviePoster.transitionName = viewItem.id.toString()
            Glide
                .with(itemView.context)
                .load("https://image.tmdb.org/t/p/w154" + viewItem.posterUrl)
                .apply(fitCenterTransform())
                .into(object : CustomTarget<Drawable>() {
                    override fun onLoadCleared(placeholder: Drawable?) {
                    }

                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        itemView.moviePoster.background = resource
                    }
                })
        }
    }
}
