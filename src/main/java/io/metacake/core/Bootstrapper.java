package io.metacake.core;

import io.metacake.core.common.window.CakeWindow;
import io.metacake.core.input.InputDeviceName;
import io.metacake.core.input.InputSystem;
import io.metacake.core.input.system.InputDevice;
import io.metacake.core.input.system.InputLayer;
import io.metacake.core.output.OutputDeviceName;
import io.metacake.core.output.OutputSystem;
import io.metacake.core.output.system.OutputDevice;
import io.metacake.core.output.system.OutputLayer;
import io.metacake.core.process.GameRunner;
import io.metacake.core.process.Transition;
import io.metacake.core.process.state.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * This class handles bootstrapping and launching the game.
 * <p>
 * The {@code Bootstrapper} sets up all of the necessary pieces of the game and does any binding that is necessary.
 *
 * @author florence
 * @author rpless
 */
public class Bootstrapper {
    public static final long DEFAULT_LOOP_MILLIS = 50;
    private static final Logger logger = LoggerFactory.getLogger(Bootstrapper.class);

    private CakeWindow window;
    private Map<InputDeviceName,InputDevice> inputs;
    private Map<OutputDeviceName, OutputDevice> outputs;
    private Transition transition;
    private long loopTime;

    Bootstrapper(CakeWindow window, Map<InputDeviceName, InputDevice> inputs, Map<OutputDeviceName, OutputDevice> outputs,
                        Transition t){
        this(window, inputs, outputs, t, DEFAULT_LOOP_MILLIS);
    }

    Bootstrapper(CakeWindow window, Map<InputDeviceName, InputDevice> inputs, Map<OutputDeviceName, OutputDevice> outputs,
                        Transition t, long loopTime) {
        this.window = window;
        this.inputs = inputs;
        this.outputs = outputs;
        this.transition = t;
        this.loopTime = loopTime;
    }

    /**
     * Invokes the the binding phases of a game creation and then launches the game.
     */
    public void setupAndLaunchGame() {
        GameRunner r = this.bootstrapSystem();
        r.mainLoop(transition, loopTime);
    }

    /**
     * Create a {@link io.metacake.core.process.GameRunner} that is ready to go
     * @return The game runner
     */
    GameRunner bootstrapSystem() {
        this.bootstrapUserObjects();
        InputSystem i = this.bootstrapInputSystem();
        OutputSystem o = this.bootstrapOutputSystem();
        GameRunner r = this.bootstrapProcessLayer(i, o);
        logger.info("starting i/o loops");
        o.startOutputLoops();
        i.startInputLoops();
        return r;
    }

    /**
     * Invokes binding operations for all {@link io.metacake.core.input.system.InputDevice}s and
     * {@link io.metacake.core.output.system.OutputDevice}s.
     */
    void bootstrapUserObjects() {
        logger.info("Bootstrapping user objects");
        inputs.values().forEach(i -> i.bind(window));
        outputs.values().forEach(i -> i.bind(window));
    }

    /**
     * @return Returns an {@link io.metacake.core.input.InputSystem} that has been set up and bound.
     */
    InputSystem bootstrapInputSystem() {
        logger.info("Bootstrapping input system from user objects");
        return new InputLayer(inputs);
    }

    /**
     * @return Returns an {@link io.metacake.core.output.OutputSystem} that has been set up and bound.
     */
    OutputSystem bootstrapOutputSystem() {
        logger.info("Bootstrapping output system from user objects");
        return new OutputLayer(outputs);
    }

    /**
     * @param i the {@link io.metacake.core.input.InputSystem} for the game
     * @param o the {@link io.metacake.core.output.OutputSystem} for the game
     * @return a GameRunner that has been bound to the Input and Output Systems and is ready to be launched
     */
     GameRunner bootstrapProcessLayer(InputSystem i, OutputSystem o) {
        logger.info("bootstrapping process layer from i/o system");
        return new GameRunner(i, o, window);
    }
}