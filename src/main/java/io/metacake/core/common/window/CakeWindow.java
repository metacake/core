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
     * force close the window
     * calls the observers
     */
    public abstract void close();

    /**
     * Get the x coordinate of the upper left
     * @return pixels from upper left of screen
     */
    public abstract int getX();

    /**
     * Get the y coordinate of the upper left
     * @return pixes from upper left of screen
     */
    public abstract int getY();

    /**
     * get width of window
     * @return in pixels
     */
    public abstract int getWidth();

    /**
     * get height of window
     * @return in pixels
     */
    public abstract int getHeight();

    /**
     * add a close observer
     */
    public void addCloseObserver(CloseObserver o);

    /**
     * @return The low level windowing object
     */
    public abstract T getRawWindow();
}