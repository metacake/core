package io.metacake.core.process.state;

import io.metacake.core.input.ActionTrigger;
import io.metacake.core.output.RenderingInstructionBundle;

import java.util.Collection;
import java.util.Collections;

/**
 * This state will end the game
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
     * End the game, using the given state to render. Close the window as well.
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
    public GameState tick(long delta) {
        return this;
    }

    @Override
    public boolean shouldReplaceActionTriggers() {
        return false;
    }

    @Override
    public Collection<ActionTrigger> replaceActionTriggers() {
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