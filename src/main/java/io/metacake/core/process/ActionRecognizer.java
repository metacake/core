package io.metacake.core.process;

/**
 * This interface is for classes that recognize certain patterns of actions.
 * A recognizer is 'triggered' when it sees a sequence of action that it recognizes.
 * @author florence
 * @author rpless
 */
public interface ActionRecognizer {

    /**
     * @return Did this recognize a sequence of actions?
     */
    public boolean wasTriggered();

    /**
     * Precondition: wasTriggered() == true </p>
     * For example, if this recognizer recognizes movement actions,
     * and the movement action was active for 20 seconds, the weight might be 20
     * @return Get an integer value that represents the weight to the trigger.
     */
    public int triggerWeight();

    /**
     * Forget that this recognizer has seen any actions.
     */
    public void forgetActions();
}
