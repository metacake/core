package io.metacake.core.input;

import io.metacake.core.input.system.InputDeviceName;

import java.util.Arrays;
import java.util.List;

/**
 * An ActionTrigger encapsulates the creation of Actions from events.
 *
 * @author florence
 * @author rpless
 */
public interface ActionTrigger<K> {
    public List<K> getCodes();
    public InputDeviceName bindingDevice();
}