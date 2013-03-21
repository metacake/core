package io.metacake.core.input.system;

import io.metacake.core.input.ActionTrigger;
import io.metacake.core.input.InputSystem;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This class is the core implementation of the input system. It handles taking events from devices
 * and translating them to actions, that are given to the processing layer
 * @author florence
 * @author rpless
 */
public class InputLayer implements InputSystem{
    Map<InputDeviceName,InputDevice> devices = new HashMap<>();

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