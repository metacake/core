package io.metacake.core.process.state;

import io.metacake.core.input.ActionTrigger;
import io.metacake.core.output.RenderingInstructionBundle;
import io.metacake.core.process.ActionRecognizer;
import io.metacake.core.process.ActionRecognizerPipe;
import io.metacake.core.process.RecognizerBucketName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * This class is meant to handle state transitions that require special instructions
 * to be passed to the Input Layer.
 * @author florence
 * @author rpless
 */
public class TransitionState implements GameState {

    /**
     * Create a transition state that replaces all triggers with the given ones
     * @param g the next state
     * @param l the {@link io.metacake.core.input.ActionTrigger}s to replace
     * @param r the {@link io.metacake.core.process.ActionRecognizer}s to replace
     * @return the transition state
     */
    public static TransitionState transition(GameState g, Collection<ActionTrigger> l, Collection<RecognizerBucketName> r) {
        return new TransitionState(g, l, r);
    }

    public static TransitionStateBuilder withState(GameState s) {
        return new TransitionStateBuilder().withState(s);
    }

    public static TransitionStateBuilder withTriggers(ActionTrigger... triggers) {
        return TransitionState.withTriggers(Arrays.asList(triggers));
    }

    public static TransitionStateBuilder withTriggers(Collection<ActionTrigger> triggers) {
        return new TransitionStateBuilder().withTriggers(triggers);
    }

    public static <T extends ActionRecognizer> TransitionStateBuilder withBucket(RecognizerBucketName<T> bucket, T ... recognizers) {
        return TransitionState.withBucket(bucket, Arrays.<T>asList(recognizers));
    }

    public static <T extends ActionRecognizer> TransitionStateBuilder withBucket(RecognizerBucketName<T> bucket, Collection<T> recognizers) {
        return new TransitionStateBuilder().withBucket(bucket, recognizers);
    }

    private GameState next;
    private Collection<ActionTrigger> triggers;
    private Collection<RecognizerBucketName> recognizers;

    private TransitionState(GameState next, Collection<ActionTrigger> triggers, Collection<RecognizerBucketName> recognizers) {
        this.next = next;
        this.triggers = triggers;
        this.recognizers = recognizers;
    }

    @Override
    public GameState tick(long delta, ActionRecognizerPipe pipe) {
        return next;
    }

    @Override
    public boolean replaceInputs() {
        return true;
    }

    @Override
    public Collection<ActionTrigger> replaceActionTriggers() {
        return triggers;
    }

    @Override
    public Collection<RecognizerBucketName> replaceActionRecognizers() {
        return recognizers;
    }

    @Override
    public RenderingInstructionBundle renderingInstructions() {
        return RenderingInstructionBundle.EMPTY_BUNDLE;
    }

    @Override
    public final boolean isGameOver(){
        return false;
    }

    public static class TransitionStateBuilder {
        private GameState state;
        private Collection<ActionTrigger> triggers = new ArrayList<>();
        private Collection<RecognizerBucketName> recognizers = new HashSet<>();

        private TransitionStateBuilder() {}

        public TransitionStateBuilder withState(GameState s) {
            state = s;
            return this;
        }

        public <T extends ActionRecognizer> TransitionStateBuilder withBucket(RecognizerBucketName<T> bucket,T ... rs) {
            return this.withBucket(bucket, Arrays.<T>asList(rs));
        }

        public <T extends ActionRecognizer> TransitionStateBuilder withBucket(RecognizerBucketName<T> bucket, Collection<T> rs) {
            rs.forEach(bucket::register);
            recognizers.add(bucket);
            return this;
        }

        public TransitionStateBuilder withTriggers(ActionTrigger... ts) {
            return this.withTriggers(Arrays.asList(ts));
        }

        public TransitionStateBuilder withTriggers(Collection<ActionTrigger> ts) {
            triggers.addAll(ts);
            return this;
        }

        public TransitionState transition() {
            if(state == null) {
                throw new IllegalStateException("Cannot transition without a state.");
            }
            return TransitionState.transition(state, triggers, recognizers);
        }
    }
}