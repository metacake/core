package io.metacake.core.common.window;

/**
 * Interface for things that wait for the window to close
 * @author spencerflorence
 */
public interface CloseObserver {
    /**
     * Notify the observer the window has closed
     */
    public void onClose();
}
