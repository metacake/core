package io.metacake.core.output;

import java.util.*;

/**
 * This class bundles RenderingInstructions with their devices.
 *
 * @author florence
 * @author rpless
 */
public class RenderingInstructionBundle {
    private Map<OutputDeviceName, List<RenderingInstruction>> instructions = new HashMap<>();

    /**
     * And immutable empty bundle
     */
    public static final RenderingInstructionBundle EMPTY_BUNDLE = new RenderingInstructionBundle(
            Collections.unmodifiableMap(new HashMap<OutputDeviceName, List<RenderingInstruction>>()));

    /**
     * Create an empty bundle
     */
    public RenderingInstructionBundle() {}

    private RenderingInstructionBundle(Map<OutputDeviceName, List<RenderingInstruction>> m) {
        instructions = m;
    }

    /**
     * This is an internal method. Only call when implementing an OutputSystem. Its signature
     * is subject to change.
     * @return the instructions this bundle has collected
     */
    public Map<OutputDeviceName, List<RenderingInstruction>> getInstructions() {
        return instructions;
    }

    /**
     * EFFECT: add {@code inst} to the bundle bound to {@code name}
     * return this.
     * @param name Name of the output device
     * @param inst Instruction to bind
     * @return {@code this}
     */
    public RenderingInstructionBundle add(OutputDeviceName name, RenderingInstruction inst) {
        if (!instructions.containsKey(name)) {
            instructions.put(name, new LinkedList<RenderingInstruction>());
        }
        instructions.get(name).add(inst);
        return this;
    }
}
