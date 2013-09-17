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

    CloseObserver closer;
    /**
     * Add a CloseObserver.
     * <p>This is an internal method, and should not be called</p>
     * @param o The {@link CloseObserver} to be usedon close.
     */
    public final void addCloseObserver(CloseObserver o){
        closer = o;
    }

    /**
     * Call this to begin shutting down the engine
     */
    public final void close(){
        if(closer!=null){
            closer.onClose();
        }
    }

    /**
     * @return Get the Low level window object.
     */
    public abstract T getRawWindow();
}