package io.metacake.core.process.state;

import io.metacake.core.common.CustomizableMap;
import io.metacake.core.input.ActionTrigger;
import io.metacake.core.output.RenderingInstructionBundle;
import io.metacake.core.process.ActionRecognizer;
import io.metacake.core.process.ActionRecognizerName;

import java.util.Arrays;
import java.util.Collection;

/**
 * This class is meant to handle state transitions that require special instructions
 * to be passed to the engine
 * @author florence
 * @author rpless
 */
public class TransitionState implements GameState {
    private GameState next;
    private Collection<ActionTrigger> triggers;

    /**
     * Create a transition state that replaces all triggers with the given ones
     * @param g the next state
     * @param l the triggers to replace
     * @return the transition state
     */
    public static TransitionState transitionWithTriggers(GameState g, Collection<ActionTrigger> l) {
        return new TransitionState(g, l);
    }

    /**
     * Create a transition state that replaces all triggers with the given ones
     * @param g the next state
     * @param l the triggers to replace
     * @return the transition state
     */
    public static TransitionState transitionWithTriggers(GameState g, ActionTrigger... l) {
        return transitionWithTriggers(g, Arrays.asList(l));
    }

    private TransitionState(GameState next, Collection<ActionTrigger> triggers) {
        this.next = next;
        this.triggers = triggers;
    }

    @Override
    public GameState tick(long delta, CustomizableMap<ActionRecognizerName, ActionRecognizer> recognizers) {
        return next;
    }

    @Override
    public boolean shouldReplaceActionTriggers() {
        return true;
    }

    @Override
    public Collection<ActionTrigger> replaceActionTriggers() {
        return triggers;
    }

    @Override
    public RenderingInstructionBundle renderingInstructions() {
        return RenderingInstructionBundle.EMPTY_BUNDLE;
    }

    @Override
    public final boolean isGameOver(){
        return false;
    }
}