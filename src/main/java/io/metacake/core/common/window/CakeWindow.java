package io.metacake.core.common.window;

/**
 * a window as far as Cake is concerned
 * when a window is closed via user action it should inform all observers
 *
 * @author florence
 * @author rpless
 */
public interface CakeWindow<T> {
    /**
     * Force close the window and calls the CloseObservers.
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
     * Add a CloseObserver/
     */
    public void addCloseObserver(CloseObserver o);

    /**
     * @return Get the Low level window object.
     */
    public abstract T getRawWindow();
}