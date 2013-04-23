package io.metacake;

import io.metacake.core.Bootstrapper;
import io.metacake.core.input.system.InputDevice;
import io.metacake.core.input.InputDeviceName;
import io.metacake.core.output.system.OutputDevice;
import io.metacake.core.output.OutputDeviceName;
import io.metacake.engine.DrawingDevice;
import io.metacake.engine.Keyboard;
import io.metacake.engine.Window;
import io.metacake.game.InitialState;

import java.util.*;

/**
 * @author florence
 * @author rpless
 */
public class Run {
    public static void main(String... args){
        Map<OutputDeviceName,OutputDevice> outputs = new HashMap<>();
        outputs.put(DrawingDevice.NAME, new DrawingDevice());
        Map<InputDeviceName,InputDevice> inputs = new HashMap<>();
        inputs.put(Keyboard.NAME, new Keyboard());
        Bootstrapper b = new Bootstrapper(new Window(), inputs, outputs, new InitialState(0,400));
        b.setupAndLaunchGame();
    }
}
