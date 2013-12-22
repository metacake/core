package io.metacake.core.input;

import io.metacake.core.common.Disposable;

/**
 * The Interface that the Process Layer uses to acquire input.
 * <p>
 * This Interface accumulates actions received from the input system and can given them to the process system at
 * request. It can also accept triggers to control action creation.
 * @author florence
 * @author rpless
 */
public interface InputSystem extends Disposable {

    /**
     * Bind an {@link io.metacake.core.input.ActionTrigger} to a Device in the {@code InputSystem} with the given name.
     * @param t The {@link io.metacake.core.input.ActionTrigger} to be bound.
     */
    public void bindActionTrigger(ActionTrigger t);

    /**
     * Throw away all the current {@link io.metacake.core.input.ActionTrigger}s.
     */
    public void releaseActionTriggers();

    /**
     * By the time this method return all input devices must have started their main threads.
     */
    public void startInputLoops();
}
