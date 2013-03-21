package io.metacake.engine;

import io.metacake.core.common.window.CakeWindow;
import io.metacake.core.input.ActionTrigger;
import io.metacake.core.input.system.InputDevice;
import io.metacake.core.input.system.InputDeviceName;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;

/**
 * @author florence
 * @author rpless
 */
public class Keyboard implements InputDevice, KeyListener {
    public static final InputDeviceName NAME = new InputDeviceName();
    private List<KeyboardTrigger> triggers = new LinkedList<>();

    @Override
    public void shutdown() {
    }

    @Override
    public void bind(CakeWindow window) {
        CakeWindow<JFrame> w = (CakeWindow<JFrame>) window;
        w.getRawWindow().addKeyListener(this);
    }

    @Override
    public void addTrigger(ActionTrigger trigger) {
        triggers.add((KeyboardTrigger)trigger);
    }

    @Override
    public InputDeviceName getName() {
        return NAME;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        e.consume();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        for(KeyboardTrigger t : triggers){
            if(t.getCodes().contains(e.getKeyCode())) {
                t.keyPressed(e);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        for(KeyboardTrigger t : triggers){
            if(t.getCodes().contains(e.getKeyCode())) {
                t.keyReleased(e);
            }
        }
    }
}


