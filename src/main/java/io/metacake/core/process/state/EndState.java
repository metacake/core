package io.metacake.core.process.state;

import io.metacake.core.process.*;

/**
 * An {@code EndState} will force the game to end.
 * @author florence
 * @author rpless
 */
public final class EndState implements GameState {
    private Type type;

    /**
     * End the game, using the given state to render. Do not close the window.
     * @return the end state
     */
    public static GameState end() {
        return new EndState(Type.END);
    }

    /**
     * End the game, using the given state to render and close the window.
     * @return the end state
     */
    public static GameState close() {
        return new EndState(Type.CLOSE);
    }

    private EndState(Type type) {
        this.type = type;
    }

    @Override
    public Transition tick(long delta, ActionRecognizerPipe recognizers) {
        return Transition.to(this);
    }

    @Override
    public Type type() {
        return type;
    }
}