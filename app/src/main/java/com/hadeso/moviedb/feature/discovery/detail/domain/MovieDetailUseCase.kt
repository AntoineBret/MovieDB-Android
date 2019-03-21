package com.hadeso.moviedb.feature.discovery.detail.domain

import com.hadeso.moviedb.architecture.base.Action
import com.hadeso.moviedb.architecture.base.UseCase
import com.hadeso.moviedb.feature.discovery.detail.state.MovieDetailAction
import com.hadeso.moviedb.feature.discovery.detail.view.MovieDetailViewItem
import com.hadeso.moviedb.feature.discovery.domain.MovieRepository
import com.hadeso.moviedb.model.MovieModel
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class MovieDetailUseCase @Inject constructor(private val movieRepository: MovieRepository) : UseCase<MovieDetailCommand> {

    override fun execute(commandObservable: Observable<MovieDetailCommand>): Observable<Action> {
        return commandObservable.flatMap { command ->
            Timber.d("Received command : ${command::class.java.simpleName}")
            return@flatMap when (command) {
                is MovieDetailCommand.LoadMovieDetail -> loadMovieDetail(command.movieId)
            }
        }
    }

    private fun loadMovieDetail(movieId: Int): Observable<Action> {
        return movieRepository.getMovie(movieId)
            .map { modelToViewItem(it) }
            .map { MovieDetailAction.UpdateMovieDetail(it) }
            .cast(Action::class.java)
            .toObservable()
            .startWith(MovieDetailAction.StartLoad)
            .onErrorReturn { MovieDetailAction.Error(MovieDetailError.Network) }
            .subscribeOn(Schedulers.io())
    }

    private fun modelToViewItem(movieModel: MovieModel): MovieDetailViewItem {
        return MovieDetailViewItem(
            movieModel.id,
            movieModel.title,
            movieModel.overview,
            movieModel.posterPath,
            movieModel.backdropPath
        )
    }
}
