package io.metacake.core.input;

import io.metacake.core.input.system.InputDeviceName;

/**
 * The Interface that the Process Layer uses to acquire input.
 * <p>
 * This Interface accumulates actions received from the input system and can given them to the process system at
 * request. It can also accept triggers to control action creation.
 * @author florence
 * @author rpless
 */
public interface InputSystem {

    /**
     * Bind an ActionTrigger to a Device in the InputSystem with the given name.
     * @param name The unique name of the device.
     * @param t The ActionTrigger to be bound.
     */
    public void bindActionTrigger(InputDeviceName name, ActionTrigger t);
}
