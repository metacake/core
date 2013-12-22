package io.metacake.core.common;

/**
 * The {@code Disposable} interface represents objects that can be disposed of.
 * @author florence
 * @author rpless
 */
public interface Disposable {
    /**
     * Safely dispose of this object, shutting it and all of its components down.
     */
    public void dispose();
}