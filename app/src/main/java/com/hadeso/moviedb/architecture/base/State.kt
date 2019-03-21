package com.hadeso.moviedb.architecture.base

import com.hadeso.moviedb.architecture.Store

/**
 * A [State] is what defines all the application's aspects at a given time.
 * A [State] must be an immutable data structure. It brings more safety and avoids race conditions.
 * A [State] is handled by a [Store].
 * A [State] can only be mutated by reducers that are pure functions.
 */
interface State
