package com.hadeso.moviedb.architecture.base

import io.reactivex.Observable

/**
 * The [View] is responsible of two main core businesses:
 * - gather the user's inputs
 * - display -- or render -- the content to the user
 * A [View] can build [Intent] and send them to its associated [IntentInterpreter].
 * As a result, the [View] receives a [State] or a [ViewState] that it then renders.
 **/
interface View<I : Intent, in S : State> {
    /**
     * Unique [Observable] used by the [IntentInterpreter] to listen to the [View].
     * All the [View]'s [Intent]s must go through this [Observable].
     * @return Observable<[Intent]>
     */
    fun intents(): Observable<I>

    /**
     * Renders the given [State] to the screen
     * @param state: the [State] given back by the [IntentInterpreter]
     **/
    fun render(state: S)
}
