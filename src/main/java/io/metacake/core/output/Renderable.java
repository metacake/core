package io.metacake.core.output;

import java.util.HashMap;
import java.util.List;

/**
 * @author florence
 * @author rpless
 */
public interface Renderable {
    /**
     * Returns all instructions to render this renderable
     * @param outputDevices a user defined container for all OutputDevices, built by the bootstrapper
     * @return A map of devices to their instructions
     */
    public HashMap<OutputDevice,List<RenderingInstruction>> renderingInstructions(Object outputDevices);
}
