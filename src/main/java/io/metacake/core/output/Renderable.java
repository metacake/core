package io.metacake.core.output;

import java.util.List;
import java.util.Map;

/**
 * @author florence
 * @author rpless
 */
public interface Renderable {
    /**
     * @return Returns a map of OutDevices mapped to the instructions they must render.
     */
    public Map<OutputDeviceName,List<RenderingInstruction>> renderingInstructions();
}