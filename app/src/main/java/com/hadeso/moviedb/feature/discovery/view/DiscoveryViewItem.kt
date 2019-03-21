package com.hadeso.moviedb.feature.discovery.view

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DiscoveryViewItem(
        val id: Int,
        val title: String,
        val posterUrl: String,
        val overview: String,
        val voteAverage: String
) : Parcelable
