package io.metacake.core.process.state;

import io.metacake.core.input.ActionTrigger;
import io.metacake.core.output.OutputDeviceName;
import io.metacake.core.output.RenderingInstruction;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author florence
 * @author rpless
 */
public class TransitionState implements GameState{
    private GameState next;
    private List<ActionTrigger> triggers;

    public static TransitionState transitionWithTriggers(GameState g, List<ActionTrigger> l){
        return new TransitionState(g,l);
    }

    public static TransitionState transitionWithTriggers(GameState g, ActionTrigger... l){
        return transitionWithTriggers(g, Arrays.asList(l));
    }

    private TransitionState(GameState next, List<ActionTrigger> triggers) {
        this.next = next;
        this.triggers = triggers;
    }

    @Override
    public GameState tick() {
        return next;
    }

    @Override
    public boolean shouldReplaceActionTriggers() {
        return true;
    }

    @Override
    public List<ActionTrigger> replaceActionTriggers() {
        return triggers;
    }

    @Override
    public Map<OutputDeviceName, List<RenderingInstruction>> renderingInstructions() {
        return Collections.emptyMap();
    }
}
