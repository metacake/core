package io.metacake.core.output;

import java.util.List;
import java.util.Map;

/**
 * @author florence
 * @author rpless
 */
public interface Renderable {
    /**
     * @param outputDevices a container for all OutputDevices
     * @return Returns a map of OutDevices mapped to the instructions they must render.
     */
    public Map<OutputDevice,List<RenderingInstruction>> renderingInstructions(Map<OutputDeviceName,OutputDevice> outputDevices);
}