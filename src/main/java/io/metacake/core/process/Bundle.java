package io.metacake.core.process;

import io.metacake.core.output.Renderable;
import io.metacake.core.output.RenderingInstructionBundle;
import io.metacake.core.process.state.GameState;

/**
 */
public class Bundle implements Renderable {
    private static Bundle singleton = new Bundle();

    public static Bundle getBundle() {
        return singleton;
    }

    static void reset() {
        singleton.withState(null);
        singleton.withInstructions(RenderingInstructionBundle.EMPTY_BUNDLE);
    }

    private GameState nextState;
    private RenderingInstructionBundle instructions = RenderingInstructionBundle.EMPTY_BUNDLE;

    public Bundle withState(GameState state) {
        nextState = state;
        return this;
    }

    public Bundle withInstructions(RenderingInstructionBundle inst) {
        instructions = inst;
        return this;
    }

    protected GameState state() { return nextState; }
    @Override
    public RenderingInstructionBundle renderingInstructions() {
        return instructions;
    }
}
