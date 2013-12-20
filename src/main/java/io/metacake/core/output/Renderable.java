package io.metacake.core.output;

/**
 * A {@code Renderable} is an object that is capable of delivering a bundle of objects that can rendered by
 * part of the Output System.
 * @author florence
 * @author rpless
 */
public interface Renderable {
    /**
     * @return Returns a map of OutDevices mapped to the instructions they must render.
     */
    public RenderingInstructionBundle renderingInstructions();
}