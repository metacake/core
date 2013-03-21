package io.metacake.core.input;

import io.metacake.core.input.system.InputDeviceName;
import io.metacake.core.output.OutputDeviceName;

import java.util.List;

/**
 * What the Process Layer sees from the Input Layer.
 *
 * This interface pool is for actions received from the input system
 * and can given them to the process system at request
 * It can also accept triggers to control action creation
 * @author spencerflorence
 */
public interface InputSystem {

    public void bindActionTrigger(InputDeviceName name, ActionTrigger t);
}
