package io.metacake.core.output;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author florence
 * @author rpless
 */
public interface Renderable {
    /**
     * Returns all instructions to render this renderable
     * @param outputDevices a container for all OutputDevices
     * @return A map of devices to their instructions
     */
    public Map<OutputDevice,List<RenderingInstruction>> renderingInstructions(Map<OutputDeviceName,OutputDevice> outputDevices);
}
