package io.metacake.core.input;

/**
 * An ActionTrigger encapsulates the creation of Actions from events.
 * @author florence
 * @author rpless
 */
public interface ActionTrigger {

    /**
     * @return Should the given CakeEvent trigger an Action from this?
     */
    public boolean shouldTriggerAction(CakeEvent event);

    /**
     * Precondition: a call to shouldTriggerAction on the given CakeEvent return true.
     * @return Return an Action created by this trigger.
     */
    public Action triggerAction(CakeEvent event);
}