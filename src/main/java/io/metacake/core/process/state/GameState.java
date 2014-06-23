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
     * @return the type of state.
     */
    default Type type() {
        return Type.NORMAL;
    };

    /**
     * Describes the kinds of states.
     * {@link io.metacake.core.process.state.GameState.Type#NORMAL} is for runtime states
     * {@link io.metacake.core.process.state.GameState.Type#END} is for end states
     * {@link io.metacake.core.process.state.GameState.Type#CLOSE} is for states that close the window
     */
    public enum Type {
        NORMAL,
        END,
        CLOSE
    };
}