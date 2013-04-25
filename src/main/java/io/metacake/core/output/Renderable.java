package io.metacake.core.output;

import io.metacake.core.output.RenderingInstructionBundle;

/**
 * @author florence
 * @author rpless
 */
public interface Renderable {
    /**
     * @return Returns a map of OutDevices mapped to the instructions they must render.
     */
    public RenderingInstructionBundle renderingInstructions();
}