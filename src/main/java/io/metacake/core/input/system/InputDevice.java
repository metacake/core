package io.metacake.core.input.system;

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
    public void receiveEventHander(CakeEventHandler e);

    /**
     * Safely shutdown this device
     */
    public void shutdown();
}
