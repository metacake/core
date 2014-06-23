package io.metacake.core.process.state;

import io.metacake.core.output.RenderingInstructionBundle;
import io.metacake.core.process.ActionRecognizerPipe;
import io.metacake.core.process.Bundle;

/**
 * A {@code VoidState} is a {@link UserState} that allows for an imperative style
 * of programming in {@link GameState}s.
 * <p>
 *      Instead of overriding the {@link io.metacake.core.process.state.GameState#tick(long, io.metacake.core.process.ActionRecognizerPipe)} method
 *      a user overrides the {@link io.metacake.core.process.state.VoidState#update(long, io.metacake.core.process.ActionRecognizerPipe)} method.
 *      To transition to another state,
 */
public abstract class VoidState extends UserState {

    private GameState currentState = this;
    private RenderingInstructionBundle currentInstructions = RenderingInstructionBundle.EMPTY_BUNDLE;

    @Override
    public final Bundle tick(long delta, ActionRecognizerPipe recognizers) {
        update(delta, recognizers);
        Bundle ret = Bundle.getBundle().withState(currentState).withInstructions(currentInstructions);
        this.currentInstructions = RenderingInstructionBundle.EMPTY_BUNDLE;
        return ret;
    }

    /**
     * Transition to the next state.
     * <p>
     *     This method should only be called from within the an overridden {@link io.metacake.core.process.state.VoidState#update(long, io.metacake.core.process.ActionRecognizerPipe)}
     *     method. This method only sets what the state will be on the next tick cycle. It does not immediately
     *     transition the state.
     * @param gameState The {@link io.metacake.core.process.state.GameState} to transition to
     */
    public void setTransition(GameState gameState) {
        currentState = gameState;
    }

    /**
     * Use these instructions for this cycle.
     * <p>
     *     This method should only be called from within the an overridden {@link io.metacake.core.process.state.VoidState#update(long, io.metacake.core.process.ActionRecognizerPipe)}
     *     method. This method only sets what the rendering instructions be used on this tick cycle. It does not immediately
     *     render.
     * @param inst the {@link io.metacake.core.output.RenderingInstructionBundle} to use on the this cycle only
     */
    public void setInstructions(RenderingInstructionBundle inst) {
        this.currentInstructions = inst;
    }

    /**
     * In a {@code VoidState}, this method will be overridden instead of {@link io.metacake.core.process.state.GameState#tick(long, io.metacake.core.process.ActionRecognizerPipe)}
     * in order to update aspects of the state.
     * @param delta The number of milliseconds since update was last called.
     * @param recognizers The bundle of {@link io.metacake.core.process.ActionRecognizer}s 
     */
    public abstract void update(long delta, ActionRecognizerPipe recognizers);
}