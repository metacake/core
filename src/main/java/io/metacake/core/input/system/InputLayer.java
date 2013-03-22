package io.metacake.core.input.system;

import io.metacake.core.input.ActionTrigger;
import io.metacake.core.input.InputSystem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The InputLayer is the core implementation of the input system. It takes events from devices and translates them
 * into actions, which can be passed to the Processing Layer.
 * @author florence
 * @author rpless
 */
public class InputLayer implements InputSystem{
    private Map<InputDeviceName,InputDevice> devices = new HashMap<>();

    /**
     * Create the InputLayer with the given list of InputDevices.
     */
    public InputLayer(List<InputDevice> devices) {
       for(InputDevice d : devices) {
           this.devices.put(d.getName(),d);
       }
    }

    @Override
    public void bindActionTrigger(InputDeviceName name, ActionTrigger t) {
        devices.get(name).addTrigger(t);
    }

    /**
     * Safely shutdown the whole input system
     */
    public void shutdown() {
        for(InputDevice i : devices.values()) {
            i.shutdown();
        }
    }
}