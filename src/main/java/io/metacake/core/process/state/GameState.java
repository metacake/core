package io.metacake.core.process.state;

import io.metacake.core.input.ActionTrigger;
import io.metacake.core.output.Renderable;

import java.util.Collection;

/**
 * A GameState represents a state that a game can be in.
 * @author florence
 * @author rpless
 */
public interface GameState extends Renderable{

    /**
     * Perform one cycle of the game loop
     * @param delta The time in milliseconds since tick was last called.
     * @return Returns the next state, which could be the same state.
     */
    public GameState tick(long delta);

    // TODO: mechanism for adding and removing individual triggers (do we want bundles?)

    /**
     * @return Does this state want all action triggers to be replaced for the next cycle?
     */
    public boolean shouldReplaceActionTriggers();

    /**
     * Precondition: this#shouldReplaceActionTriggers == true
     * @return Returns the ActionTriggers that will replace the old ones.
     */
    public Collection<ActionTrigger> replaceActionTriggers();

    /**
     * @return true if the game is over
     */
    public boolean isGameOver();
}