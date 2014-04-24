package io.metacake.core.process.state;

import io.metacake.core.common.CustomizableMap;
import io.metacake.core.input.ActionTrigger;
import io.metacake.core.output.RenderingInstructionBundle;
import io.metacake.core.process.ActionRecognizer;
import io.metacake.core.process.ActionRecognizerName;
import io.metacake.core.process.ActionRecognizerPipe;
import io.metacake.core.process.RecognizerBucketName;

import java.util.Collection;

/**
 * An {@code EndState} will force the game to end.
 * @author florence
 * @author rpless
 */
public final class EndState implements GameState {
    private GameState with;
    private boolean closeWindow;

    /**
     * End the game, using the given state to render. Do not close the window.
     * @param s the state to render
     * @return the end state
     */
    public static GameState endWith(GameState s) {
        return new EndState(s, false);
    }

    /**
     * End the game, using the given state to render and close the window.
     * @param s the state to render
     * @return the end state
     */
    public static GameState closeWith(GameState s) {
        return new EndState(s, true);
    }

    private EndState(GameState with, boolean closeWindow) {
        this.with = with;
        this.closeWindow = closeWindow;
    }

    /**
     * Internal method. Do not use.
     * @return Should the window close?
     */
    public final boolean shouldCloseWindow() {
        return this.closeWindow;
    }

    @Override
    public GameState tick(long delta, ActionRecognizerPipe recognizers) {
        return this;
    }

    @Override
    public boolean replaceInputs() {
        return false;
    }

    @Override
    public Collection<ActionTrigger> replaceActionTriggers() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<RecognizerBucketName> replaceActionRecognizers() {
        throw new UnsupportedOperationException();
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