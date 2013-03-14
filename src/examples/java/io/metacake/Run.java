package io.metacake;

import io.metacake.core.Bootstrapper;
import io.metacake.core.input.system.InputDevice;
import io.metacake.core.output.OutputDevice;
import io.metacake.core.output.OutputDeviceName;

import java.util.*;

/**
 * @author florence
 * @author rpless
 */
public class Run {
    public static void main(String... args){
        Map<OutputDeviceName,OutputDevice> os = new HashMap<>();
        os.put(DrawingDevice.NAME, new DrawingDevice());
        Bootstrapper b = new Bootstrapper(new Window(), Collections.<InputDevice>emptyList(),os,new InitialState(0,400));
        b.setupAndLaunchGame();
    }
}
