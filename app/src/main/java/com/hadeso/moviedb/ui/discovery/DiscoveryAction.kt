package com.hadeso.moviedb.ui.discovery

import com.hadeso.moviedb.mvibase.MviAction

sealed class DiscoveryAction : MviAction {
    object LoadDiscoveryAction : DiscoveryAction()
}