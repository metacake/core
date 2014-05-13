package io.metacake.core.process.state;

import io.metacake.core.process.ActionRecognizerPipe;

/**
 * A {@code VoidState} is a {@link UserState} that allows for an imperative style
 * of programming in {@link GameState}s.
 * <p>
 *      Instead of overriding the {@link io.metacake.core.process.state.GameState#tick(long, io.metacake.core.process.ActionRecognizerPipe)} method
 *      a user overrides the {@link io.metacake.core.process.state.VoidState#update(long, io.metacake.core.process.ActionRecognizerPipe)} method.
 *      To transition to another state,
 */
public abstract class VoidState extends UserState {

    private GameState currentState = this;

    @Override
    public final GameState tick(long delta, ActionRecognizerPipe recognizers) {
        update(delta, recognizers);
        return currentState;
    }

    /**
     * Transition to the next state.
     * <p>
     *     This method should only be called from within the an overridden {@link io.metacake.core.process.state.VoidState#update(long, io.metacake.core.process.ActionRecognizerPipe)}
     *     method. This method only sets what the state will be on the next tick cycle. It does not immediately
     *     transition the state.
     * @param gameState The {@link io.metacake.core.process.state.GameState} to transition to
     */
    public void setTransition(GameState gameState) {
        currentState = gameState;
    }

    /**
     * In a {@code VoidState}, this method will be overridden instead of {@link io.metacake.core.process.state.GameState#tick(long, io.metacake.core.process.ActionRecognizerPipe)}
     * in order to update aspects of the state.
     * @param delta The number of milliseconds since update was last called.
     * @param recognizers The bundle of {@link io.metacake.core.process.ActionRecognizer}s 
     */
    public abstract void update(long delta, ActionRecognizerPipe recognizers);
}