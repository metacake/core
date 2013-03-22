package io.metacake.core.process;

import io.metacake.core.input.ActionTrigger;
import io.metacake.core.output.Renderable;

import java.util.List;

/**
 * A GameState represents a state that a game can be in.
 * @author florence
 * @author rpless
 */
public interface GameState extends Renderable{
    /**
     * @return The recognizers the current game state has bound
     */
    public List<ActionRecognizer> getRecognizers();

    /**
     * Perform one cycle of the game loop
     * @return Returns the next state, which could be the same state.
     */
    public GameState tick();

    /**
     * @return Should the known actions be flushed?
     */
    public boolean shouldClearActions();

    /**
     * @return Does this state want all action triggers to be replaced for the next cycle?
     */
    public boolean shouldReplaceActionTriggers();

    /**
     * Precondition: this#shouldReplaceActionTriggers == true
     * @return Returns the ActionTriggers that will replace the old ones.
     */
    public List<ActionTrigger> replaceActionTriggers();
}
