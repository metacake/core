package io.metacake.core;

import io.metacake.core.common.window.CakeWindow;
import io.metacake.core.input.InputSystem;
import io.metacake.core.input.system.InputDevice;
import io.metacake.core.input.system.InputLayer;
import io.metacake.core.output.OutputDevice;
import io.metacake.core.output.OutputDeviceName;
import io.metacake.core.output.OutputSystem;
import io.metacake.core.output.system.OutputLayer;
import io.metacake.core.process.GameRunner;
import io.metacake.core.process.GameState;

import java.util.List;
import java.util.Map;

/**
 * This class handles bootstrapping and launching the game.
 * @author florence
 * @author rpless
 */
public class Bootstrapper {
    public static final long DEFAULT_LOOP_MILLIS = 50;
    CakeWindow window;
    List<InputDevice> inputs;
    Map<OutputDeviceName,OutputDevice> outputs;
    GameState initialState;
    private long loopTime;

    public Bootstrapper(CakeWindow window, List<InputDevice> inputs, Map<OutputDeviceName,OutputDevice> outputs, GameState g) {
        this(window, inputs, outputs, g,DEFAULT_LOOP_MILLIS);
    }

    public Bootstrapper(CakeWindow window, List<InputDevice> inputs, Map<OutputDeviceName,OutputDevice> outputs, GameState g, long loopTime) {
        this.window = window;
        this.inputs = inputs;
        this.outputs = outputs;
        this.initialState = g;
        this.loopTime = loopTime;
    }

    /**
     * Read the damn method name
     */
    public void setupAndLaunchGame(){
        this.bootstrapUserObjects();
        InputSystem i = this.bootstrapInputSystem();
        OutputSystem o = this.bootstrapOutputSystem();
        GameRunner r = this.bootstrapProcessLayer(i,o);
        o.startOutputLoops();
        r.mainLoop(initialState,loopTime);
    }

    /**
     * Get the user object ready for running by binding them to the windo
     */
    private void bootstrapUserObjects(){
        for(InputDevice i : inputs){
            i.bind(window);
        }
        for(OutputDevice o : outputs.values()){
            o.bind(window);
        }
    }

    /**
     * @return An InputSystem that is ready to go
     */
    private InputSystem bootstrapInputSystem(){
        return new InputLayer(inputs);
    }

    /**
     * @return An OutputSystem that is ready to go
     */
    private OutputSystem bootstrapOutputSystem(){
        return new OutputLayer(outputs);
    }

    /**
     * @param i the InputSystem for the game
     * @param o the OutputSystem for the game
     * @return a GameRunner that is ready to go
     */
    private GameRunner bootstrapProcessLayer(InputSystem i, OutputSystem o){
        return new GameRunner(i,o);
    }
}