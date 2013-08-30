package io.metacake.core.input.system;

import io.metacake.core.common.CustomizableMap;
import io.metacake.core.input.ActionTrigger;
import io.metacake.core.input.InputDeviceName;
import io.metacake.core.input.InputSystem;

import java.util.Map;

/**
 * The InputLayer is the core implementation of the input system. It takes events from devices and translates them
 * into actions, which can be passed to the Processing Layer.
 * @author florence
 * @author rpless
 */
public class InputLayer implements InputSystem{
    private CustomizableMap<InputDeviceName,InputDevice> devices;

    /**
     * Create the InputLayer with the given list of InputDevices.
     */
    public InputLayer(Map<InputDeviceName,InputDevice> devices) {
       this.devices = new CustomizableMap<>(devices);
    }

    @Override
    public void bindActionTrigger(InputDeviceName name, ActionTrigger t) {
        devices.get(name).addTrigger(t);
    }

    @Override
    public void releaseActionTriggers() {
        for(InputDevice d : devices.values()) {
            d.releaseTriggers();
        }
    }

    @Override
    public void startInputLoops() {
        for(InputDevice i : devices.values()) {
            i.startInputLoop();
        }
    }

    @Override
    public void dispose() {
        for(InputDevice i : devices.values()) {
            i.shutdown();
        }
    }
}