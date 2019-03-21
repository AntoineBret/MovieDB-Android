package com.hadeso.moviedb.architecture.base

/**
 * After retrieving the [State] from the Store, the [ViewStateMapper]
 * has to build a UI frienldy representation of it, called a [ViewState]
 * and passes it to the [View]
 */
interface ViewState : State
