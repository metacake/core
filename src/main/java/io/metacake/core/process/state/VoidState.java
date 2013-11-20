package io.metacake.core.process.state;

import io.metacake.core.input.ActionTrigger;

/**
 * A VoidState is a {@link UserState} that allows for an imperative style
 * of programming in {@link GameState}s.
 * <p>
 *      Instead of overriding the {@link io.metacake.core.process.state.GameState#tick()} method
 *      a user overrides the {@link io.metacake.core.process.state.VoidState#update()} method.
 *      To transition to another state,
 */
public abstract class VoidState extends UserState {

    private GameState currentState = this;

    @Override
    public final GameState tick() {
        update();
        return currentState;
    }

    /**
     * Transition to the next state.
     * <p>
     *     This method should only be called from within the an overridden {@link io.metacake.core.process.state.VoidState#update()}
     *     method. This method only sets what the state will be on the next tick cycle. It does not immediately
     *     transition the state.
     * @param gameState
     * @param l
     */
    public void setTransition(GameState gameState, ActionTrigger... l) {
        currentState = TransitionState.transitionWithTriggers(gameState, l);
    }

    /**
     * In a {@code VoidState}, this method will be overridden instead of {@link io.metacake.core.process.state.GameState#tick()}
     * in order to update aspects of the state.
     */
    public abstract void update();
}