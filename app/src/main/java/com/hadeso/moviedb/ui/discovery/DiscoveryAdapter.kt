package com.hadeso.moviedb.ui.discovery

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.hadeso.moviedb.R
import com.hadeso.moviedb.utils.inflate
import kotlinx.android.synthetic.main.view_discovery_movie.view.*

/**
 * Created by 77796 on 12-Mar-18.
 */
class DiscoveryAdapter(private var discoveryViewItems: List<DiscoveryViewItem>) : RecyclerView.Adapter<DiscoveryAdapter.DiscoveryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, position: Int): DiscoveryViewHolder {
        return DiscoveryViewHolder(parent!!)
    }

    override fun onBindViewHolder(holder: DiscoveryViewHolder?, position: Int) {
        val item = discoveryViewItems[position]
        holder?.bind(item)
    }

    override fun getItemCount(): Int {
        return discoveryViewItems.size
    }

    fun updateMovies(discoveryViewItems: List<DiscoveryViewItem>) {
        this.discoveryViewItems = discoveryViewItems
    }

    inner class DiscoveryViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.view_discovery_movie)) {

        fun bind(viewItem: DiscoveryViewItem) {
            itemView.movieTitle.text = viewItem.title
        }
    }
}