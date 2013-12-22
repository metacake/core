package io.metacake.core.common.window;

import io.metacake.core.common.Disposable;

/**
 * A CakeWindow is an adapter for user or library defined windows that need to be used by Cake.
 *
 * @param <T> The raw window type that is being wrapped over.
 *
 * @author florence
 * @author rpless
 */
public abstract class CakeWindow<T> implements Disposable {

    private CloseObserver closer;

    /**
     * @return Get the x coordinate on the screen of the upper left corner of this window
     */
    public abstract int getX();

    /**
     * @return Get the y coordinate on the screen of the upper left corner of this window
     */
    public abstract int getY();

    /**
     * @return Get width of this window in pixels
     */
    public abstract int getWidth();

    /**
     * @return Get height of this window in pixels
     */
    public abstract int getHeight();

    /**
     * Add a {@link CloseObserver}.
     * <p>
     *     This is an internal method and should not be called.
     * @param o The {@link CloseObserver} to be used on close.
     */
    public final void addCloseObserver(CloseObserver o) {
        closer = o;
    }

    /**
     * Initiates closing the window and in principle should start the shutdown for the entire engine.
     */
    public final void close() {
        if(closer != null) {
            closer.onClose();
        }
    }

    /**
     * @return Get the Low level window object.
     */
    public abstract T getRawWindow();
}