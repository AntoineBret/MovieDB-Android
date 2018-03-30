package com.hadeso.moviedb.ui.discovery

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by 77796 on 08-Mar-18.
 */
@Parcelize
data class DiscoveryViewItem(
        val id: Int,
        val title: String,
        val posterUrl: String,
        val overview: String,
        val voteAverage: String
) : Parcelable