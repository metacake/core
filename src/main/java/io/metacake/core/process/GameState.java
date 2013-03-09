package io.metacake.core.process;

import io.metacake.core.input.ActionTrigger;
import io.metacake.core.output.Renderable;

import java.util.List;

/**
 * @author florence
 * @author rpless
 */
public interface GameState extends Renderable{
    /**
     * @return The recognizers the current game state needs
     */
    public List<ActionRecognizer> getRecognizers();

    /**
     * Perform one cycle of the game loop
     * @return the next state. Maybe the same state?
     */
    public GameState tick();

    /**
     * @return Should the known actions be flushed?
     */
    public boolean shouldClearActions();

    /**
     * @return Does this state want all action triggers replaced?
     */
    public boolean shouldReplaceActionTriggers();

    /**
     * Precondition: this#shouldReplaceActionTriggers == true
     * @return all action triggers
     */
    public List<ActionTrigger> getNewActionTriggers();
}
