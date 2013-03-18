package io.metacake;

import io.metacake.core.Bootstrapper;
import io.metacake.core.input.system.InputDevice;
import io.metacake.core.output.OutputDevice;
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
        Map<OutputDeviceName,OutputDevice> os = new HashMap<>();
        os.put(DrawingDevice.NAME, new DrawingDevice());
        List<InputDevice> inputs = new LinkedList<>();
        inputs.add(new Keyboard());
        Bootstrapper b = new Bootstrapper(new Window(), inputs, os, new InitialState(0,400));
        b.setupAndLaunchGame();
    }
}
