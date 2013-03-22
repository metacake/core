package io.metacake.core.common.window;

/**
 * A CakeWindow is an adapter for user or library defined windows that need to be used by Cake.
 *
 * @param <T> The raw window type that is being wrapped over.
 *
 * @author florence
 * @author rpless
 */
public interface CakeWindow<T> {
    /**
     * Force close the window and notify all CloseObservers of all closures.
     */
    public abstract void close();

    /**
     * @return Get the x coordinate on the screen of the upper left corner of this window
     */
    public abstract int getX();

    /**
     * @return Get the y coordinate on the screen of the upper left corner of this window
     */
    public abstract int getY();

    /**
     *
     * @return Get width of this window in pixels
     */
    public abstract int getWidth();

    /**
     * @return Get width of this window in pixels
     */
    public abstract int getHeight();

    /**
     * Add a CloseObserver.
     */
    public void addCloseObserver(CloseObserver o);

    /**
     * @return Get the Low level window object.
     */
    public abstract T getRawWindow();
}