package io.metacake.core.process.state;

import io.metacake.core.input.ActionTrigger;
import io.metacake.core.output.OutputDeviceName;
import io.metacake.core.output.RenderingInstruction;
import io.metacake.core.output.RenderingInstructionBundle;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * This state will end the game
 * @author florence
 * @author rpless
 */
public final class EndState implements GameState {
    private GameState with;

    /**
     * End the game, using the given state to render
     * @param s the state to render
     * @return the end state
     */
    public static GameState endWith(GameState s){
        return new EndState(s);
    }

    private EndState(GameState with) {
        this.with = with;
    }

    @Override
    public GameState tick() {
        return this;
    }

    @Override
    public boolean shouldReplaceActionTriggers() {
        return false;
    }

    @Override
    public List<ActionTrigger> replaceActionTriggers() {
        return Collections.emptyList();
    }

    @Override
    public boolean isGameOver() {
        return true;
    }

    @Override
    public RenderingInstructionBundle renderingInstructions() {
        return with.renderingInstructions();
    }
}
