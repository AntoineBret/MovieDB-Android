package com.hadeso.moviedb.architecture

import com.hadeso.moviedb.architecture.base.Action
import com.hadeso.moviedb.architecture.base.Lens
import com.hadeso.moviedb.architecture.base.State

/**
 * A [Mutator] holds all the required tools to mutate a [State] according to an [Action].
 * It uses a reducer to generate a new SubState based on the actual [State] and an [Action].
 * It then uses a [StateLens] to generate a new [State] based on the actual [State] and the new SubState.
 */
data class Mutator<S : State, SubState : State>(
    private val lens: Lens<S, SubState>,
    private val reducer: (S, Action) -> SubState
) {
    /**
     * Mutates a [State] according to an [Action].
     * @param state: the original [State] to mutate
     * @param action: the action used to decide the mutation
     * @return the mutated [State]
     */
    fun apply(state: S, action: Action): S {
        return this.lens.set(state, this.reducer(state, action))
    }
}
