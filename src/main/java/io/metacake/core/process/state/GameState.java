package io.metacake.core.process.state;

import io.metacake.core.process.ActionRecognizerPipe;
import io.metacake.core.process.Transition;

/**
 * A GameState represents a state that a game can be in.
 * @author florence
 * @author rpless
 */
public interface GameState {

    /**
     * Perform one cycle of the game loop.
     * @param delta The time in milliseconds since tick was last called.
     * @param pipe The pipe with all the buckets of ActionRecognizers
     * @return Returns the next state, which could be the same state.
     */
    public Transition tick(long delta, ActionRecognizerPipe pipe);

    /**
     * @return true if the game is over
     */
    public boolean isGameOver();
}