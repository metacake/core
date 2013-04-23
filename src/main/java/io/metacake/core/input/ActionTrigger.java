package io.metacake.core.input;

import java.util.List;

/**
 * An ActionTrigger encapsulates the creation of Actions from events.
 *
 * @author florence
 * @author rpless
 */
public interface ActionTrigger<K> {

    /**
     * @return Get the codes that this ActionTrigger will respond to.
     */
    public List<K> getCodes();

    /**
     * @return Get the name of the InputDevice that this ActionTrigger is bound to.
     */
    public InputDeviceName bindingDevice();
}