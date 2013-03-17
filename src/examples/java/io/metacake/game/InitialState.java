package io.metacake.game;

import io.metacake.core.common.MilliTimer;
import io.metacake.core.input.ActionTrigger;
import io.metacake.core.output.OutputDevice;
import io.metacake.core.output.OutputDeviceName;
import io.metacake.core.output.RenderingInstruction;
import io.metacake.core.process.ActionRecognizer;
import io.metacake.core.process.GameState;
import io.metacake.engine.DrawInstruction;
import io.metacake.engine.DrawingDevice;

import javax.activation.MimeTypeParameterList;
import java.util.*;

/**
 * @author florence
 * @author rpless
 */
public class InitialState implements GameState{
    int x;
    int y;

    public InitialState(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public List<ActionRecognizer> getRecognizers() {
        return Collections.emptyList();
    }

    @Override
    public GameState tick() {
        return new InitialState(x,(y+1) % 500);
    }

    @Override
    public boolean shouldClearActions() {
        return false;
    }

    @Override
    public boolean shouldReplaceActionTriggers() {
        return false;
    }

    @Override
    public List<ActionTrigger> getNewActionTriggers() {
        return Collections.emptyList();
    }

    @Override
    // Concern: List<RenderingInstruction> could be its own class
    public Map<OutputDevice, List<RenderingInstruction>> renderingInstructions(Map<OutputDeviceName, OutputDevice> outputDevices) {
        HashMap<OutputDevice,List<RenderingInstruction>> ret = new HashMap<>();
        LinkedList<RenderingInstruction> l = new LinkedList<>();
        l.add(new DrawInstruction(y));
        ret.put(outputDevices.get(DrawingDevice.NAME),l);
        return ret;
    }
}
