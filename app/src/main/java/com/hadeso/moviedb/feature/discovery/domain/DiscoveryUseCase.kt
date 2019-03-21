package com.hadeso.moviedb.feature.discovery.domain

import com.hadeso.moviedb.architecture.base.Action
import com.hadeso.moviedb.architecture.base.UseCase
import com.hadeso.moviedb.feature.discovery.state.DiscoveryAction
import com.hadeso.moviedb.feature.discovery.view.DiscoveryViewItem
import com.hadeso.moviedb.model.DiscoveryMovieModel
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class DiscoveryUseCase @Inject constructor(private val movieRepository: MovieRepository) : UseCase<DiscoveryCommand> {

    override fun execute(commandObservable: Observable<DiscoveryCommand>): Observable<Action> {
        return commandObservable.flatMap { command ->
            Timber.d("Received command : ${command::class.java.simpleName}")
            return@flatMap when (command) {
                DiscoveryCommand.LoadMovies -> loadMovies()
                is DiscoveryCommand.GoToDetail -> goToDetails(command.viewItem)
            }
        }
    }

    private fun goToDetails(viewItem: DiscoveryViewItem): Observable<Action> = Observable.fromCallable { DiscoveryAction.GoToMovie(viewItem) }

    private fun loadMovies(): Observable<Action> {
        return movieRepository.getDiscoveryMovies()
            .map { model -> modelToView(model) }
            .toList()
            .map { DiscoveryAction.UpdateMovies(it) }
            .cast(Action::class.java)
            .toObservable()
            .startWith(DiscoveryAction.StartLoad)
            .onErrorReturn { DiscoveryAction.Error(DiscoveryError.Network) }
            .subscribeOn(Schedulers.io())
    }


    private fun modelToView(discoveryMovieModel: DiscoveryMovieModel): DiscoveryViewItem {
        return DiscoveryViewItem(
            discoveryMovieModel.id,
            discoveryMovieModel.title,
            discoveryMovieModel.posterPath,
            discoveryMovieModel.overview,
            discoveryMovieModel.voteAverage.toString()
        )
    }
}

