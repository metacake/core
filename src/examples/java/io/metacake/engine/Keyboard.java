package io.metacake.engine;

import io.metacake.core.common.window.CakeWindow;
import io.metacake.core.input.CakeEvent;
import io.metacake.core.input.system.CakeEventHandler;
import io.metacake.core.input.system.InputDevice;
import io.metacake.core.input.Action;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author florence
 * @author rpless
 */
public class Keyboard implements InputDevice, KeyListener {
    public CakeEventHandler handler;

    @Override
    public void receiveEventHandler(CakeEventHandler e) {
        handler = e;
    }

    @Override
    public void shutdown() { }

    @Override
    public void bind(CakeWindow window) {
        CakeWindow<JFrame> w= (CakeWindow<JFrame>) window;
        w.getRawWindow().addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) { e.consume(); }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO: switch == bad. We did this programmatically before. Lacking API
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP : handler.handle(CakeKeyEvent.upPressed);
                break;
            case KeyEvent.VK_DOWN : handler.handle(CakeKeyEvent.downPressed);
                break;
            case KeyEvent.VK_LEFT : handler.handle(CakeKeyEvent.rightPressed);
                break;
            case KeyEvent.VK_RIGHT : handler.handle(CakeKeyEvent.leftPressed);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP : handler.handle(CakeKeyEvent.upReleased);
                break;
            case KeyEvent.VK_DOWN : handler.handle(CakeKeyEvent.downReleased);
                break;
            case KeyEvent.VK_LEFT : handler.handle(CakeKeyEvent.rightReleased);
                break;
            case KeyEvent.VK_RIGHT : handler.handle(CakeKeyEvent.leftReleased);
                break;
        }
    }

    public static class CakeKeyEvent extends CakeEvent {
        // TODO: This pressed/released stuff is bad. Need to think more. API issue?
        public static CakeKeyEvent upPressed = new CakeKeyEvent();
        public static CakeKeyEvent downPressed = new CakeKeyEvent();
        public static CakeKeyEvent leftPressed = new CakeKeyEvent();
        public static CakeKeyEvent rightPressed = new CakeKeyEvent();

        public static CakeKeyEvent upReleased = new CakeKeyEvent();
        public static CakeKeyEvent downReleased = new CakeKeyEvent();
        public static CakeKeyEvent leftReleased = new CakeKeyEvent();
        public static CakeKeyEvent rightReleased = new CakeKeyEvent();

    }
}


