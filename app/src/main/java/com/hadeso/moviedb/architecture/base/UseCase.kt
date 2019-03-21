package com.hadeso.moviedb.architecture.base

import io.reactivex.Observable

/**
 * [UseCase] is where side effects (mainly async work) and business take place.
 * The only way to execute a [UseCase] is to give it a [Command] that describes the work to do.
 * If the work is synchronous it is possible to return an Observable<[Action]>.just(...).
 */
interface UseCase<C : Command> {
    /**
     * Process the [Command] given by the [IntentInterpreter]
     * @param commandObservable: The [Command] to be processed
     */
    fun execute(commandObservable: Observable<C>): Observable<Action>
}
