package com.hadeso.moviedb.architecture.base

/**
 * A [Lens] is a concept from functional programming that allows to focus on a specific content inside
 * of a container value type, both for accessing it (the content) and mutating it (the container).
 * In this implementation the Container is the [State] and the Content is the SubState
 */
interface Lens<S, A> {
    fun get(s: S): A
    fun set(s: S, a: A): S
}
