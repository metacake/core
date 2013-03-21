package io.metacake.engine;

import io.metacake.core.process.ActionRecognizer;

import java.awt.event.KeyEvent;

/**
 * @author florence
 * @author rpless
 */
public interface KeyboardRecognizer extends ActionRecognizer {
    public void keyPressed(KeyEvent e);
    public void keyReleased(KeyEvent e);
}
