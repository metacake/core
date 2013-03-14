package io.metacake.core.input.system;

import io.metacake.core.common.window.CakeWindow;

/**
 * This is the interface into any input device (keyboard, etc). These will usually be java event listeners
 * @author florence
 * @author rpless
 */
public interface InputDevice {
    /**
     * Give this device what handler it should report to
     * @param e the handler
     */
    public void receiveEventHandler(CakeEventHandler e);

    /**
     * Safely shutdown this device
     */
    public void shutdown();

    /**
     * Bind this device to the given window.
     * @param w The window for the game
     */
    public void bind(CakeWindow w);
}
