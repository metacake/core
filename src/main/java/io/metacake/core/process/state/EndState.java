package io.metacake.core.process.state;

import io.metacake.core.input.ActionTrigger;
import io.metacake.core.process.*;

import java.util.Collection;

/**
 * An {@code EndState} will force the game to end.
 * @author florence
 * @author rpless
 */
public final class EndState implements GameState {
    private boolean closeWindow;

    /**
     * End the game, using the given state to render. Do not close the window.
     * @return the end state
     */
    public static GameState end() {
        return new EndState(false);
    }

    /**
     * End the game, using the given state to render and close the window.
     * @return the end state
     */
    public static GameState close() {
        return new EndState(true);
    }

    private EndState(boolean closeWindow) {
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
    public Transition tick(long delta, ActionRecognizerPipe recognizers) {
        return Transition.to(this);
    }

    @Override
    public boolean isGameOver() {
        return true;
    }
}