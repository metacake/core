package io.metacake.core.process.state;

import io.metacake.core.input.ActionTrigger;

/**
 * A VoidState is a {@link UserState} that allows for an imperative style
 * of programming in {@link GameState}s.
 * <p>
 *      Instead of overriding the {@link io.metacake.core.process.state.GameState#tick(long)} method
 *      a user overrides the {@link io.metacake.core.process.state.VoidState#update(long)} method.
 *      To transition to another state,
 */
public abstract class VoidState extends UserState {

    private GameState currentState = this;

    @Override
    public final GameState tick(long delta) {
        update(delta);
        return currentState;
    }

    /**
     * Transition to the next state.
     * <p>
     *     This method should only be called from within the an overridden {@link io.metacake.core.process.state.VoidState#update(long)}
     *     method. This method only sets what the state will be on the next tick cycle. It does not immediately
     *     transition the state.
     * @param gameState The state to transition to
     * @param triggers The triggers to add on transition
     */
    public void setTransition(GameState gameState, ActionTrigger... triggers) {
        currentState = TransitionState.transitionWithTriggers(gameState, triggers);
    }

    /**
     * In a {@code VoidState}, this method will be overridden instead of {@link io.metacake.core.process.state.GameState#tick(long)}
     * in order to update aspects of the state.
     * @param delta The number of milliseconds since update was last called.
     */
    public abstract void update(long delta);
}