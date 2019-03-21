package com.hadeso.moviedb.architecture.base

/**
 * Transform a [State] to a [ViewState]
 * This class is optional and is needed in case the [State] needs to be formatted to fit the [View]
 */
interface ViewStateMapper<S : State, VS : ViewState> {
    /**
     * Function that transform a [State] in [ViewState]
     * @param fromState the current [State]
     * @return the generated [ViewState]
     */
    fun viewState(fromState: S): VS
}
