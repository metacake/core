package io.metacake.core.common.window;

/**
 * Interface for Observers that wait for a CakeWindow to close.
 * @author florence
 * @author rpless
 */
public interface CloseObserver {
    /**
     * Notify the observer the window has closed
     */
    public void onClose();
}
