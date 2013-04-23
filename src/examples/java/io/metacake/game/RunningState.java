package io.metacake.game;

import io.metacake.core.input.ActionTrigger;
import io.metacake.core.output.OutputDeviceName;
import io.metacake.core.output.RenderingInstruction;
import io.metacake.core.process.ActionRecognizer;
import io.metacake.core.process.GameState;
import io.metacake.engine.DrawInstruction;
import io.metacake.engine.DrawingDevice;

import java.util.*;

/**
 * @author florence
 * @author rpless
 */
class RunningState implements GameState {
    int x;
    int y;
    ActionRecognizer vert;
    ActionRecognizer horz;

    public RunningState(int x, int y, ActionRecognizer v, ActionRecognizer h) {
        this.x = x;
        this.y = y;
        this.vert = v;
        this.horz = h;
    }

    @Override
    public List<ActionRecognizer> getRecognizers() {
        List<ActionRecognizer> r = new LinkedList<>();
        r.add(vert);
        return r;
    }

    @Override
    public GameState tick() {
        if (vert.wasTriggered()) {
            y += vert.triggerWeight();
        }
        if(horz.wasTriggered()) {
            x += horz.triggerWeight();
        }
        return new RunningState(x, y, vert, horz);
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
    public List<ActionTrigger> replaceActionTriggers() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Map<OutputDeviceName, List<RenderingInstruction>> renderingInstructions() {
        HashMap<OutputDeviceName, List<RenderingInstruction>> ret = new HashMap<>();
        LinkedList<RenderingInstruction> l = new LinkedList<>();
        l.add(new DrawInstruction(x,y));
        ret.put(DrawingDevice.NAME, l);
        return ret;
    }
}
