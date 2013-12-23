package io.metacake.core.process.state;

import io.metacake.core.common.CustomizableMap;
import io.metacake.core.input.ActionTrigger;
import io.metacake.core.output.RenderingInstructionBundle;
import io.metacake.core.process.ActionRecognizer;
import io.metacake.core.process.ActionRecognizerName;

import java.util.Collection;

/**
 * This class is meant to handle state transitions that require special instructions
 * to be passed to the Input Layer.
 * @author florence
 * @author rpless
 */
public class TransitionState implements GameState {

    /**
     * Create a transition state that replaces all triggers with the given ones
     * @param g the next state
     * @param l the {@link io.metacake.core.input.ActionTrigger}s to replace
     * @param r the {@link io.metacake.core.process.ActionRecognizer}s to replace
     * @return the transition state
     */
    public static TransitionState transition(GameState g, Collection<ActionTrigger> l, Collection<ActionRecognizer> r) {
        return new TransitionState(g, l, r);
    }

    private GameState next;
    private Collection<ActionTrigger> triggers;
    private Collection<ActionRecognizer> recognizers;

    private TransitionState(GameState next, Collection<ActionTrigger> triggers, Collection<ActionRecognizer> recognizers) {
        this.next = next;
        this.triggers = triggers;
        this.recognizers = recognizers;
    }

    @Override
    public GameState tick(long delta, CustomizableMap<ActionRecognizerName, ActionRecognizer> recognizers) {
        return next;
    }

    @Override
    public boolean replaceInputs() {
        return true;
    }

    @Override
    public Collection<ActionTrigger> replaceActionTriggers() {
        return triggers;
    }

    @Override
    public Collection<ActionRecognizer> replaceActionRecognizers() {
        return recognizers;
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