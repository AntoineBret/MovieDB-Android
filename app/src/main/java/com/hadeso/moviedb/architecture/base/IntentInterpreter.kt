package com.hadeso.moviedb.architecture.base

import com.hadeso.moviedb.architecture.Store
import io.reactivex.Observable

/**
 * [IntentInterpreter] is the intermediary between an [Intent] and the resulting [State].
 * It will schedule the [Command] / [Action] creation to make an [Intent] be transformed into a
 * [State] by leveraging the [Command] -> [Action] -> [State] workflow.
 * [IntentInterpreter] is the privileged way to interpret an [Intent] with the whole State Container mechanisms.
 */
interface IntentInterpreter<I : Intent, C : Command, S : State> {

    /**
     * Interprets an [Intent] to generate a new [State]
     * LifeCycle of an [Intent]: [Intent] -> [Command] -> [Action] -> [State]
     * @param intentObservable: the user input formalized by the [Intent]
     * @return the [State] to be handled by the [View]
     */
    fun interpret(intentObservable: Observable<I>): Observable<S> {
        return intentObservable.compose(::command)
            .compose(::action)
            .compose(::state)
    }

    /**
     * Process the [Intent] given by the [View]
     * @param intentObservable: The [Intent] to be processed
     */
    fun processIntents(intentObservable: Observable<I>)

    /**
     * Retrieves a [Command] according to an [Intent]
     * @param intentObservable: the user input formalized by the [Intent]
     * @return the command to be executed by the [UseCase]
     */
    fun command(intentObservable: Observable<I>): Observable<C>

    /**
     * Retrieves an [Action] according to a [Command]
     * @param commandObservable: [Command] to be executed by the [UseCase]
     * @return [Action] to be dispatched to the [Store]
     */
    fun action(commandObservable: Observable<C>): Observable<Action>

    /**
     * Retrieves a [State] according to an [Action]
     * @param actionObservable: The [Action] to be dispatched
     * @return The mutated [State] to be interpreted
     */
    fun state(actionObservable: Observable<Action>): Observable<S>
}
