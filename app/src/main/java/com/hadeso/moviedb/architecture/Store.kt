package com.hadeso.moviedb.architecture

import com.hadeso.moviedb.architecture.base.Action
import com.hadeso.moviedb.architecture.base.State
import com.hadeso.moviedb.utils.toIntOrZero
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * A [Store] holds the state and mutates it through [Action] and Reducers.
 * The Reducers are registered into the [Store] through Mutator.
 * The [Store] does not directly expose the [State].
 */
class Store<StateType : State>(private var state: StateType) {

    private var reducersForSubStateTypes = mutableMapOf<String, Int>()

    /**
     * We cannot store directly a list of [Mutator] because it is a generic type
     * that needs to define both a [State] and a SubState. Possible SubStates are not
     * known from the [Store]. We only store the deducted Reducers that only know about [State] and [Action].
     */
    private var reducers: ArrayList<(StateType, Action) -> StateType>

    /**
     * Init a Store with an initial State. The mutation of the subStates of a State must be handled
     * by one and only one Reducer.
     */
    init {
        state.javaClass.declaredFields.map { field ->
            // Check if the field implements State as it is a data class and contains other fields
            if (field.type.interfaces.contains(State::class.java)) {
                this.reducersForSubStateTypes[field.type.simpleName] = this.reducersForSubStateTypes[field.type.simpleName].toIntOrZero().inc()
            }
        }
        reducers = ArrayList(reducersForSubStateTypes.size)
    }

    /**
     * Should be used mostly for test purposes
     */
    fun getReducers() = reducers.toList()

    /**
     * Registers a Reducer dedicated to a State/SubState mutation.
     * The Reducer is extracted from the passed [Mutator] internally.
     * The [Store] does not keep a reference on the [Mutator] but only on the reducer it defines.
     * Each Reducer is then applied in sequence when a new [Action] is dispatched.
     * There MUST be a Reducer for every [State]'s SubState
     * There MUST be only one [Mutator] per SubState
     * @param subState: The substate class used with the mutator
     * @param mutator: The [Mutator] dedicated to a State/Substate mutation
     */
    fun <SubState : State> register(subState: Class<SubState>, mutator: Mutator<StateType, SubState>) {
        val numberOfNeededReducers = this.reducersForSubStateTypes[subState.simpleName]
        numberOfNeededReducers?.let {
            if (numberOfNeededReducers <= 0) {
                // We're trying to register more Reducers for a SubState than needed
                throw Exception("The SubState can no more accept new Reducers")
            } else {
                this.reducers.add(mutator::apply) // A reference is also kept on the Mutator as the apply func keeps a reference on the Mutator.
                this.reducersForSubStateTypes[subState.simpleName] = numberOfNeededReducers - 1
            }
        } ?: throw Exception("The Mutator is not relevant for the State")
    }

    /**
     * Dispatches an [Action] to the registered Reducers.
     * The [Action] will first go through the Middlewares and then through the Reducers producing a mutated [State].
     * @param action: The [Action] that will be handled by the reducers
     * @return The mutated [State]
     */
    fun dispatch(actionObservable: Observable<Action>): Observable<StateType> {
        val areAllSubStatesReduced = this.reducersForSubStateTypes
            .filter { it.value > 0 }
            .isEmpty()

        if (!areAllSubStatesReduced) {
            throw Exception("All SubState must be reduced by a Reducer" + this.reducersForSubStateTypes.toString())
        }
        return actionObservable
            .observeOn(Schedulers.single())
            .map { action ->
                this.state = this.reducers.fold(state) { currentState, reducer ->
                    reducer(currentState, action)
                }
                return@map state
            }
    }

    /**
     * Dispatches an [Action] to the registered Reducers but instead of
     * returning the mutated [State], it returns an subset of the mutated [State].
     * @param actionObservable: The [Action] observable that will be handled by the reducers
     * @param focusingOn: The closure that defines the subset of the [State] that we want to retrieve
     * @return The mutated subset of the [State]
     */
    fun <StateContent> dispatch(actionObservable: Observable<Action>, focusingOn: (StateType) -> StateContent): Observable<StateContent> {
        return this.dispatch(actionObservable).map { focusingOn(it) }
    }
}
