package io.metacake.game;

import io.metacake.core.common.MilliTimer;
import io.metacake.core.input.Action;
import io.metacake.core.input.ActionTrigger;
import io.metacake.core.input.ActionType;
import io.metacake.core.input.CakeEvent;
import io.metacake.core.output.OutputDevice;
import io.metacake.core.output.OutputDeviceName;
import io.metacake.core.output.RenderingInstruction;
import io.metacake.core.process.ActionRecognizer;
import io.metacake.core.process.GameState;
import io.metacake.engine.DrawInstruction;
import io.metacake.engine.DrawingDevice;
import io.metacake.engine.Keyboard;

import javax.activation.MimeTypeParameterList;
import java.util.*;

/**
 * @author florence
 * @author rpless
 */
public class InitialState implements GameState {
    int x;
    int y;
    ActionRecognizer vert = new MovementRecognizer(Keyboard.CakeKeyEvent.downPressed, Keyboard.CakeKeyEvent.upPressed);

    public InitialState(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public List<ActionRecognizer> getRecognizers() {
        List<ActionRecognizer> r = new LinkedList<>();
        r.add(vert);
        return r;
    }

    @Override
    public GameState tick() {
        return new RunningState(x, y, vert);
    }

    @Override
    public boolean shouldClearActions() {
        return true;
    }

    @Override
    public boolean shouldReplaceActionTriggers() {
        return true;
    }

    @Override
    public List<ActionTrigger> getNewActionTriggers() {
        List<ActionTrigger> r = new LinkedList<>();
        r.add(new MovementStartTrigger());
        r.add(new MovementEndTrigger());
        return r;
    }

    @Override
    // Concern: List<RenderingInstruction> could be its own class
    public Map<OutputDevice, List<RenderingInstruction>> renderingInstructions(Map<OutputDeviceName, OutputDevice> outputDevices) {
        return Collections.emptyMap();
    }
}

class RunningState implements GameState {
    int x;
    int y;
    ActionRecognizer vert;

    public RunningState(int x, int y, ActionRecognizer v) {
        this.x = x;
        this.y = y;
        this.vert = v;
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
            y += vert.triggerWeight() * 10;
            vert.forgetActions();
        }
        return new RunningState(x, y, vert);
    }

    @Override
    public boolean shouldClearActions() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean shouldReplaceActionTriggers() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ActionTrigger> getNewActionTriggers() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Map<OutputDevice, List<RenderingInstruction>> renderingInstructions(Map<OutputDeviceName, OutputDevice> outputDevices) {
        HashMap<OutputDevice, List<RenderingInstruction>> ret = new HashMap<>();
        LinkedList<RenderingInstruction> l = new LinkedList<>();
        l.add(new DrawInstruction(y));
        ret.put(outputDevices.get(DrawingDevice.NAME), l);
        return ret;
    }
}

class MovementStartTrigger implements ActionTrigger {
    @Override
    public boolean shouldTriggerAction(CakeEvent event) {
        return event == Keyboard.CakeKeyEvent.downPressed ||
                event == Keyboard.CakeKeyEvent.upPressed ||
                event == Keyboard.CakeKeyEvent.rightPressed ||
                event == Keyboard.CakeKeyEvent.leftPressed;
    }

    @Override
    public Action triggerAction(CakeEvent event) {
        return new Action(event, Movement.startMove);
    }
}

class MovementEndTrigger implements ActionTrigger {
    @Override
    public boolean shouldTriggerAction(CakeEvent event) {
        return event == Keyboard.CakeKeyEvent.downReleased ||
                event == Keyboard.CakeKeyEvent.upReleased ||
                event == Keyboard.CakeKeyEvent.rightReleased ||
                event == Keyboard.CakeKeyEvent.leftReleased;
    }

    @Override
    public Action triggerAction(CakeEvent event) {
        return new Action(event, Movement.endMove);
    }
}

class Movement extends ActionType {
    public static Movement startMove = new Movement();
    public static Movement endMove = new Movement();
}

class MovementRecognizer implements ActionRecognizer {

    boolean isTriggered = false;
    final CakeEvent positive;
    final CakeEvent negative;
    int actionCount = 0;

    MovementRecognizer(CakeEvent positive, CakeEvent negative) {
        this.positive = positive;
        this.negative = negative;
    }

    @Override
    public void actionOccurred(Action a) {
        if (a.getTriggeringEvent() == positive) {
            this.isTriggered = true;
            this.actionCount += 1;
        } else if (a.getTriggeringEvent() == negative) {
            this.isTriggered = true;
            this.actionCount -= 1;
        }
    }

    @Override
    public boolean wasTriggered() {
        return isTriggered;
    }

    @Override
    public int triggerWeight() {
        return actionCount;
    }

    @Override
    public void forgetActions() {
        isTriggered = false;
        actionCount = 0;
    }
}
