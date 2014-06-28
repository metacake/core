package io.metacake.core.process;

import io.metacake.core.input.ActionTrigger;
import io.metacake.core.output.Renderable;
import io.metacake.core.output.RenderingInstructionBundle;
import io.metacake.core.process.state.GameState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * This is a transition from one state to another (possibly the same state). It comes with instructions
 * for the input and output layers on how to handle the transition.
 *
 * No Transition should be stored by a state between ticks. No tick cycle should attempt to create
 * more than one Transition.
 */
public class Transition implements Renderable {
    private static Transition singleton = new Transition();

    /**
     * Create a transitions to a state
     * @param state the state to transition to
     * @return the Transition
     */
    public static Transition to(GameState state) {
        singleton.nextState = state;
        return singleton;
    }

    static void reset() {
        singleton.to(null);
        singleton.withInstructions(RenderingInstructionBundle.EMPTY_BUNDLE);
        singleton.triggers = new ArrayList<>();
        singleton.recognizers = new HashSet<>();
        singleton.newInputs = false;
    }

    private GameState nextState;
    private RenderingInstructionBundle instructions = RenderingInstructionBundle.EMPTY_BUNDLE;
    private boolean newInputs = false;
    private Collection<ActionTrigger> triggers = new ArrayList<>();
    private Collection<RecognizerBucketName> recognizers = new HashSet<>();

    /**
     * Transition sending these rendering instructions to the output system
     * @param inst the instructions
     * @return the same Transition
     */
    public Transition withInstructions(RenderingInstructionBundle inst) {
        instructions = inst;
        return this;
    }

    /**
     * Transition sending this bucket and its contents to the input system
     * @param bucket the bucket
     * @param rs the recognizers
     * @return the same Transition
     */
    public <T extends ActionRecognizer> Transition withBucket(RecognizerBucketName<T> bucket,T ... rs) {
        return this.withBucket(bucket, Arrays.<T>asList(rs));
    }


    /**
     * Transition sending this bucket and its contents to the input system
     * @param bucket the bucket
     * @param rs the recognizers
     * @return the same Transition
     */
    public <T extends ActionRecognizer> Transition withBucket(RecognizerBucketName<T> bucket, Collection<T> rs) {
        newInputs = true;
        rs.forEach(bucket::register);
        recognizers.add(bucket);
        return this;
    }

    /**
     * Transition sending these ActionTriggers to the input system
     * @param ts the triggers
     * @return the same Transition
     */
    public Transition withTriggers(ActionTrigger... ts) {
        return this.withTriggers(Arrays.asList(ts));
    }


    /**
     * Transition sending these ActionTriggers to the input system
     * @param ts the triggers
     * @return the same Transition
     */
    public Transition withTriggers(Collection<ActionTrigger> ts) {
        newInputs = true;
        triggers.addAll(ts);
        return this;
    }

    /**
     * @return true if {@link Transition#replaceActionRecognizers()}and {@link Transition#replaceActionTriggers()} should be used
     */
    public boolean replaceInputs() {
        return newInputs;
    }

    // TODO: mechanism for adding and removing individual triggers (do we want bundles?)

    /**
     * will only be called if {@link Transition#replaceInputs()} is true
     * @return a new collection of {@link io.metacake.core.input.ActionTrigger}s that are now in use.
     */
    public Collection<ActionTrigger> replaceActionTriggers() {
        return triggers;
    }

    /**
     * will only be called if {@link Transition#replaceInputs()} is true
     * @return a new collection of {@link io.metacake.core.process.RecognizerBucketName}s that are now in use.
     */
    public Collection<RecognizerBucketName> replaceActionRecognizers() {
        return recognizers;
    }

    protected GameState state() { return nextState; }
    @Override
    public RenderingInstructionBundle renderingInstructions() {
        return instructions;
    }
}
