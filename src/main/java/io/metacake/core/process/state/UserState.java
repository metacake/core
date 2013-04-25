package io.metacake.core.process.state;

import io.metacake.core.input.ActionTrigger;

import java.util.Collections;
import java.util.List;

/**
 * @author florence
 * @author rpless
 */
public abstract class UserState implements GameState{

    @Override
    public final boolean shouldReplaceActionTriggers() {
        return false;
    }

    @Override
    public final List<ActionTrigger> replaceActionTriggers() {
        return Collections.emptyList();
    }

    @Override
    public final boolean isGameOver(){
        return false;
    }
}
