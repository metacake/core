package io.metacake.core.output.system;

import io.metacake.core.common.CustomizableMap;
import io.metacake.core.output.OutputDeviceName;
import io.metacake.core.output.OutputSystem;
import io.metacake.core.output.Renderable;

import java.util.Map;

/**
 * The {@code OutputLayer} is the core implementation of the input system. It takes {@link io.metacake.core.output.Renderable}
 * from the Process Layer and translates them into so kind of output for the user.
 * @author florence
 * @author rpless
 */
public class OutputLayer implements OutputSystem {

    private CustomizableMap<OutputDeviceName, OutputDevice> deviceContainer;

    public OutputLayer(Map<OutputDeviceName, OutputDevice> deviceContainer) {
        this.deviceContainer = new CustomizableMap<>(deviceContainer);
    }

    @Override
    public void addToRenderQueue(Renderable r) {
        r.renderingInstructions().forEach(e -> deviceContainer.get(e.getKey()).render(e.getValue()));
    }

    @Override
    public void startOutputLoops() {
        deviceContainer.values().forEach(OutputDevice::startOutputLoop);
    }

    @Override
    public void dispose(){
        deviceContainer.values().forEach(OutputDevice::shutdown);
    }
}