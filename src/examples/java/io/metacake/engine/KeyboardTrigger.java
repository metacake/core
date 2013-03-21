package io.metacake.engine;

import io.metacake.core.input.ActionTrigger;
import io.metacake.core.input.system.InputDeviceName;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author florence
 * @author rpless
 */
public class KeyboardTrigger implements ActionTrigger<Integer> {

    List<Integer> codes;
    List<KeyboardRecognizer> recognizers = new LinkedList<>();

    public KeyboardTrigger(Integer... codes) {
        this.codes = Arrays.asList(codes);
    }

    @Override
    public List<Integer> getCodes() {
        return codes;
    }

    public void keyPressed(KeyEvent e){
        for(KeyboardRecognizer r : recognizers) {
            r.keyPressed(e);
        }
    }
    public void keyReleased(KeyEvent e){
        for(KeyboardRecognizer r : recognizers) {
            r.keyReleased(e);
        }
    }

    public void bindRecognizer(KeyboardRecognizer r) {
        recognizers.add(r);
    }

    @Override
    public InputDeviceName bindingDevice() {
        return Keyboard.NAME;
    }

}
