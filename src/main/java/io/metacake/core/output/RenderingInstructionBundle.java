package io.metacake.core.output;

import io.metacake.core.common.CustomizableMap;

import java.util.*;

/**
 * This class bundles RenderingInstructions with their devices.
 *
 * Is iterable over the map of instructions.
 *
 * @author florence
 * @author rpless
 */
public class RenderingInstructionBundle implements Iterable<Map.Entry<OutputDeviceName,List<RenderingInstruction>>> {
    protected CustomizableMap<OutputDeviceName, List<RenderingInstruction>> instructions = new CustomizableMap<>(
            new HashMap<OutputDeviceName, List<RenderingInstruction>>());

    /**
     * And immutable empty bundle. It cannot be added to.
     */
    public static final RenderingInstructionBundle EMPTY_BUNDLE = new RenderingInstructionBundle(
            Collections.unmodifiableMap(new HashMap<OutputDeviceName, List<RenderingInstruction>>()));

    /**
     * Create an empty bundle
     */
    public RenderingInstructionBundle() {}

    private RenderingInstructionBundle(Map<OutputDeviceName, List<RenderingInstruction>> m) {
        instructions = new CustomizableMap<>(m);
    }

    /**
     * Create an empty bundle
     */
    private RenderingInstructionBundle(CustomizableMap<OutputDeviceName,List<RenderingInstruction>> m) {
        instructions = m;
    }

    /**
     * This is an internal method. Only call when implementing an OutputSystem.
     * Its signature is subject to change.
     * @return the instructions this bundle has collected
     */
    public CustomizableMap<OutputDeviceName, List<RenderingInstruction>> getInstructions() {
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
        ensureMappingExists(name);
        instructions.get(name).add(inst);
        return this;
    }

    /**
     * EFFECT: Add all of the {@code insts} to the bundle and bind them to the Device Name.
     * @param name Name of the output device
     * @param insts The instructions to bind.
     * @return {@code this}
     */
    public RenderingInstructionBundle add(OutputDeviceName name, RenderingInstruction...insts) {
        ensureMappingExists(name);
        instructions.get(name).addAll(Arrays.asList(insts));
        return this;
    }

    /**
     * EFFECT: ensure that there is a mapping between the given device name and at least an empty LinkedList.
     * @param name The {@code OutputDeviceName} to check
     */
    private void ensureMappingExists(OutputDeviceName name) {
        if (!instructions.containsKey(name)) {
            instructions.put(name, new LinkedList<RenderingInstruction>());
        }
    }

    @Override
    public Iterator<Map.Entry<OutputDeviceName, List<RenderingInstruction>>> iterator() {
        return this.getInstructions().iterator();
    }
}