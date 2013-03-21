package io.metacake.core.input.system;

import io.metacake.core.common.window.CakeWindow;
import io.metacake.core.input.ActionTrigger;

/**
 * This is the interface into any input device (keyboard, etc). These will usually be java event listeners
 * @author florence
 * @author rpless
 */
public interface InputDevice {

    /**
     * Safely shutdown this device
     */
    public void shutdown();

    /**
     * Bind this device to the given window.
     * @param w The window for the game
     */
    public void bind(CakeWindow w);

    public void addTrigger(ActionTrigger trigger);

    public InputDeviceName getName();
}
