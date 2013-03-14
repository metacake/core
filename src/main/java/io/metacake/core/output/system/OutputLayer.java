package io.metacake.core.output.system;

import io.metacake.core.output.OutputDevice;
import io.metacake.core.output.OutputSystem;
import io.metacake.core.output.Renderable;
import io.metacake.core.output.RenderingInstruction;

import java.util.List;
import java.util.Map;

/**
 * Implementation of the entire Output layer
 * @author florence
 * @author rpless
 */
public class OutputLayer implements OutputSystem{
    Object deviceContainer; // User implemented object with no Interface. Only a reference is needed here

    @Override
    public void addToRenderQueue(Renderable r) {
        Map<OutputDevice,List<RenderingInstruction>> instructions = r.renderingInstructions(deviceContainer);

        for(Map.Entry<OutputDevice,List<RenderingInstruction>> e : instructions.entrySet()) {
            e.getKey().render(e.getValue());
        }
    }
}
