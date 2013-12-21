package io.metacake.core.process.state;

import io.metacake.core.input.ActionTrigger;
import io.metacake.core.process.ActionRecognizer;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * This class is a state that is intended to be extended by users.
 * @author florence
 * @author rpless
 */
public abstract class UserState implements GameState {

    @Override
    public Optional<Collection<ActionTrigger>> replaceActionTriggers() {
        return Optional.empty();
    }

    @Override
    public Optional<Collection<ActionRecognizer>> replaceActionRecognizers() {
        return Optional.empty();
    }

    @Override
    public final boolean isGameOver() {
        return false;
    }
}