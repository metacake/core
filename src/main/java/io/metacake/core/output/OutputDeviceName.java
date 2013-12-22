package io.metacake.core.output;

import io.metacake.core.common.Symbol;

/**
 * This class provides an extensible enumeration for naming {@link io.metacake.core.output.system.OutputDevice}s.
 * @author florence
 * @author rpless
 */
public class OutputDeviceName extends Symbol {
    public OutputDeviceName() {}

    public OutputDeviceName(String name) {
        super(name);
    }
}