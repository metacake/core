package io.metacake.core.process.state;

import io.metacake.core.process.*;

/**
 * An {@code EndState} will force the game to end.
 * @author florence
 * @author rpless
 */
public final class EndState implements GameState {
    private Kind kind;

    /**
     * End the game, using the given state to render. Do not close the window.
     * @return the end state
     */
    public static GameState end() {
        return new EndState(Kind.END);
    }

    /**
     * End the game, using the given state to render and close the window.
     * @return the end state
     */
    public static GameState close() {
        return new EndState(Kind.CLOSE);
    }

    private EndState(Kind kind) {
        this.kind = kind;
    }

    @Override
    public Transition tick(long delta, ActionRecognizerPipe recognizers) {
        return Transition.to(this);
    }

    @Override
    public Kind kind() {
        return kind;
    }
}