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
 */
public class Transition implements Renderable {
    private static Transition active = new Transition();
    private static Transition inactive = new Transition();

   public static Transition to(GameState state) {
        active.nextState = state;
        return active;
    }

    static void swapAndReset() {
        Transition tmp = active;
        active = inactive;
        inactive = tmp;
        active.to(null);
        active.withInstructions(RenderingInstructionBundle.EMPTY_BUNDLE);
        active.triggers = new ArrayList<>();
        active.recognizers = new HashSet<>();
        active.newInputs = false;
    }

    private GameState nextState;
    private RenderingInstructionBundle instructions = RenderingInstructionBundle.EMPTY_BUNDLE;
    private boolean newInputs = false;
    private Collection<ActionTrigger> triggers = new ArrayList<>();
    private Collection<RecognizerBucketName> recognizers = new HashSet<>();

    public Transition withInstructions(RenderingInstructionBundle inst) {
        newInputs = true;
        instructions = inst;
        return this;
    }

    public <T extends ActionRecognizer> Transition withBucket(RecognizerBucketName<T> bucket,T ... rs) {
        return this.withBucket(bucket, Arrays.<T>asList(rs));
    }

    public <T extends ActionRecognizer> Transition withBucket(RecognizerBucketName<T> bucket, Collection<T> rs) {
        newInputs = true;
        rs.forEach(bucket::register);
        recognizers.add(bucket);
        return this;
    }

    public Transition withTriggers(ActionTrigger... ts) {
        return this.withTriggers(Arrays.asList(ts));
    }

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
