package io.metacake.core.process.state;

import io.metacake.core.output.RenderingInstructionBundle;
import io.metacake.core.process.ActionRecognizerPipe;
import io.metacake.core.process.Transition;

/**
 * A {@code VoidState} is a {@link GameState} that allows for an imperative style
 * of programming in {@link GameState}s.
 * <p>
 *      Instead of overriding the {@link io.metacake.core.process.state.GameState#tick(long, io.metacake.core.process.ActionRecognizerPipe)} method
 *      a user overrides the {@link io.metacake.core.process.state.VoidState#update(long, io.metacake.core.process.ActionRecognizerPipe)} method.
 *      To transition to another state,
 */
public abstract class VoidState implements GameState{

    private GameState currentState = this;
    private RenderingInstructionBundle currentInstructions = RenderingInstructionBundle.EMPTY_BUNDLE;

    @Override
    public final Transition tick(long delta, ActionRecognizerPipe recognizers) {
        update(delta, recognizers);
        Transition ret = Transition.to(currentState).withInstructions(currentInstructions);
        this.currentInstructions = RenderingInstructionBundle.EMPTY_BUNDLE;
        return ret;
    }

    /**
     * In a {@code VoidState}, this method will be overridden instead of {@link io.metacake.core.process.state.GameState#tick(long, io.metacake.core.process.ActionRecognizerPipe)}
     * in order to update aspects of the state.
     * @param delta The number of milliseconds since update was last called.
     * @param recognizers The bundle of {@link io.metacake.core.process.ActionRecognizer}s 
     */
    public abstract void update(long delta, ActionRecognizerPipe recognizers);
}