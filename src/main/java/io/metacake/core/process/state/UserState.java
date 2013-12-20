package io.metacake.core.process.state;

import io.metacake.core.input.ActionTrigger;

import java.util.Collection;
import java.util.Collections;

/**
 * This class is a state that is intended to be extended by users.
 * @author florence
 * @author rpless
 */
public abstract class UserState implements GameState {

    @Override
    public final boolean shouldReplaceActionTriggers() {
        return false;
    }

    @Override
    public final Collection<ActionTrigger> replaceActionTriggers() {
        return Collections.emptyList();
    }

    @Override
    public final boolean isGameOver() {
        return false;
    }
}