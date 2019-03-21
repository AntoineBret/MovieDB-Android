package com.hadeso.moviedb.feature.discovery.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hadeso.moviedb.feature.discovery.view.DiscoveryViewItem

class DiscoveryDiffUtil : DiffUtil.ItemCallback<DiscoveryViewItem>() {
    override fun areItemsTheSame(p0: DiscoveryViewItem, p1: DiscoveryViewItem): Boolean {
        return p0.id == p1.id
    }

    override fun areContentsTheSame(p0: DiscoveryViewItem, p1: DiscoveryViewItem): Boolean {
        return p0.overview == p1.overview
                && p0.title == p0.title
                && p0.voteAverage == p0.voteAverage
                && p0.posterUrl == p0.posterUrl
    }
}
