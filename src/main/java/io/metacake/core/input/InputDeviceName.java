package io.metacake.core.input;

import io.metacake.core.common.Symbol;

/**
 * This class provides an extensible enumeration for naming {@link io.metacake.core.input.system.InputDevice}s.
 * @author florence
 * @author rpless
 */
public class InputDeviceName extends Symbol {
    public InputDeviceName() {}

    public InputDeviceName(String name) {
        super(name);
    }
}