package io.metacake.core.process;

import io.metacake.core.input.Action;

/**
 * This interface is for classes that recognizes certain patterns of actions
 * a recongizer is 'triggered' when it sees a sequence of action that it recognizes
 * @author florence
 * @author rpless
 */
public interface ActionRecognizer {
    /**
     * Tell this recognizer that an action occurred.
     * @param a the action
     */
    public void actionOccured(Action a);

    /**
     * @return did this recognize a sequence of actions?
     */
    public boolean wasTriggered();

    /**
     * Get an integer value assocaited with the trigger.
     * Precondition: wasTriggered() == true <p>
     * For example, if this recognizer recognizes movement actions,
     * and the movement action was active for 20 seconds, the weight might be 20
     * @return
     */
    public int triggerWeight();

    /**
     * forget action this has seen.
     */
    public void forgetActions();
}
