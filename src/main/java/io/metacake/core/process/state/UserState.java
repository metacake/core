package io.metacake.core.process.state;

import io.metacake.core.input.ActionTrigger;
import io.metacake.core.process.ActionRecognizer;
import io.metacake.core.process.RecognizerBucketName;

import java.util.Collection;

/**
 * This class is a {@link io.metacake.core.process.state.GameState} that is intended to be extended by users.
 * @author florence
 * @author rpless
 */
public abstract class UserState implements GameState {

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
    public final boolean isGameOver() {
        return false;
    }
}