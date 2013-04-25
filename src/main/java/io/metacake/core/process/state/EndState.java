package io.metacake.core.process.state;

import io.metacake.core.input.ActionTrigger;
import io.metacake.core.output.OutputDeviceName;
import io.metacake.core.output.RenderingInstruction;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author florence
 * @author rpless
 */
public final class EndState implements GameState {
    private GameState with;

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
    public Map<OutputDeviceName, List<RenderingInstruction>> renderingInstructions() {
        return with.renderingInstructions();
    }
}
