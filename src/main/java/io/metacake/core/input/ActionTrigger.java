package io.metacake.core.input;

/**
 * An ActionTrigger encapsulates the creation of Actions from events.
 *
 * @author florence
 * @author rpless
 *
 * @param <K> The type of the "low-level" object that is passed to this trigger by the {@link io.metacake.core.input.system.InputDevice}
 *            it is bound to.
 */
public interface ActionTrigger<K> {

    /**
     * @param event The "low-level" event that is being checked.
     * @return Returns true if the event triggers this {@code ActionTrigger}.
     */
    public boolean isTriggeredBy(K event);

    /**
     * @return Get the name of the InputDevice that this ActionTrigger is bound to.
     */
    public InputDeviceName bindingDevice();
}