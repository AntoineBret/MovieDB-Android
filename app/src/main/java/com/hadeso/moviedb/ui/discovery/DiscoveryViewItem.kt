package com.hadeso.moviedb.ui.discovery

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