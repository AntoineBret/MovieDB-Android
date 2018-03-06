package com.hadeso.moviedb.ui.discovery

/**
 * Created by 77796 on 21-Dec-17.
 */
sealed class DiscoveryIntent {
    class MovieSelected(val movieId: String) : DiscoveryIntent()
}