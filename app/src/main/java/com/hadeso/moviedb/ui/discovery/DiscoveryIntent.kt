package com.hadeso.moviedb.ui.discovery

import com.hadeso.moviedb.mvibase.MviIntent

sealed class DiscoveryIntent : MviIntent {
    object InitialIntent : DiscoveryIntent()
    class MovieSelected(val discoveryViewItem: DiscoveryViewItem) : DiscoveryIntent()
}