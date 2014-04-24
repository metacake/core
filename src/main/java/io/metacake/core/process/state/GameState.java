package io.metacake.core.process.state;

import io.metacake.core.input.ActionTrigger;
import io.metacake.core.output.Renderable;
import io.metacake.core.process.ActionRecognizerPipe;
import io.metacake.core.process.RecognizerBucketName;

import java.util.Collection;

/**
 * A GameState represents a state that a game can be in.
 * @author florence
 * @author rpless
 */
public interface GameState extends Renderable {

    /**
     * Perform one cycle of the game loop.
     * @param delta The time in milliseconds since tick was last called.
     * @param pipe The pipe with all the buckets of ActionRecognizers
     * @return Returns the next state, which could be the same state.
     */
    public GameState tick(long delta, ActionRecognizerPipe pipe);

    // TODO: mechanism for adding and removing individual triggers (do we want bundles?)

    /**
     * @return true if {@link GameState#replaceActionRecognizers()} and {@link GameState#replaceActionTriggers()} should be used
     */
    public boolean replaceInputs();

    /**
     * will only be called if {@link GameState#replaceInputs()} is true
     * @return a new collection of {@link io.metacake.core.input.ActionTrigger}s that are now in use.
     */
    public Collection<ActionTrigger> replaceActionTriggers();

    /**
     * will only be called if {@link GameState#replaceInputs()} is true
     * @return a new collection of {@link io.metacake.core.process.ActionRecognizer}s that are now in use.
     */
    public Collection<RecognizerBucketName> replaceActionRecognizers();

    /**
     * @return true if the game is over
     */
    public boolean isGameOver();
}