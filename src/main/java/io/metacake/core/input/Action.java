package io.metacake.core.input;

/**
 * This class represents an action that occurred.
 *
 * @author florence
 * @author rpless
 */
public class Action {
    private final CakeEvent triggeringEvent;
    private final ActionType type;

    public Action(CakeEvent triggeringEvent, ActionType type) {
        this.triggeringEvent = triggeringEvent;
        this.type = type;
    }

    public CakeEvent getTriggeringEvent() {
        return this.triggeringEvent;
    }

    public ActionType getType() {
        return type;
    }
}