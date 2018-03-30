package com.hadeso.moviedb.ui.discovery

import com.hadeso.moviedb.model.DiscoveryMovieModel
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class DiscoveryReducer @Inject constructor() {
    private val loadDiscoveryMoviesReducer =
            ObservableTransformer<DiscoveryResult.LoadDiscoveryMoviesResult, DiscoveryViewState> { results ->
                results.map { result ->
                    when (result) {
                        is DiscoveryResult.LoadDiscoveryMoviesResult.Success ->
                            DiscoveryViewState(isLoading = false,
                                    movies = result.movies.results.map { modelToView(it) },
                                    error = null)
                        is DiscoveryResult.LoadDiscoveryMoviesResult.Failure ->
                            DiscoveryViewState(isLoading = false,
                                    movies = emptyList(),
                                    error = result.error)
                    }
                }
            }


    internal var reducer =
            ObservableTransformer<DiscoveryResult, DiscoveryViewState> { actions ->
                actions.publish { shared ->
                    shared.ofType(DiscoveryResult.LoadDiscoveryMoviesResult::class.java).compose(loadDiscoveryMoviesReducer)
                }
            }

    private fun modelToView(discoveryMovieModel: DiscoveryMovieModel): DiscoveryViewItem {
        return DiscoveryViewItem(discoveryMovieModel.id,
                discoveryMovieModel.title,
                discoveryMovieModel.posterPath,
                discoveryMovieModel.overview,
                discoveryMovieModel.voteAverage.toString())
    }
}