package io.metacake.core.input.system;

import io.metacake.core.input.CakeEvent;

/**
 * Interface into an object that devices use to pass events into the input system
 * @author florence
 * @author rpless
 */
public interface CakeEventHandler {
    public void handle(CakeEvent e);
}
