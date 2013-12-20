package io.metacake.core.common;

/**
 * @author florence
 * for things that are disposable. duh.
 */
public interface Disposable {
    /**
     * Safely dispose of this object, shutting it and all of its components down.
     */
    public void dispose();
}