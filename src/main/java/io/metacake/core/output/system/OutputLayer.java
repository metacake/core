package io.metacake.core.output.system;

import io.metacake.core.output.*;

import java.util.List;
import java.util.Map;

/**
 * The OutputLayer is the core implementation of the input system. It takes Renderables from the Process Layer and
 * translates them into so kind of output for the user.
 * @author florence
 * @author rpless
 */
public class OutputLayer implements OutputSystem{
    Map<OutputDeviceName,OutputDevice> deviceContainer;

    public OutputLayer(Map<OutputDeviceName, OutputDevice> deviceContainer) {
        this.deviceContainer = deviceContainer;
    }

    @Override
    public void addToRenderQueue(Renderable r) {
        Map<OutputDevice,List<RenderingInstruction>> instructions = r.renderingInstructions(deviceContainer);

        for(Map.Entry<OutputDevice,List<RenderingInstruction>> e : instructions.entrySet()) {
            e.getKey().render(e.getValue());
        }
    }

    @Override
    public void startOutputLoops() {
        for(OutputDevice o : deviceContainer.values()){
            o.startOutputLoop();
        }
    }
}