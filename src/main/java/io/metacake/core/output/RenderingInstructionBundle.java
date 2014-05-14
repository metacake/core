package io.metacake.core.output;

import io.metacake.core.common.CustomizableMap;

import java.util.*;

/**
 * This class bundles {@link io.metacake.core.output.RenderingInstruction}s with their devices.
 * <p>
 * It is iterable over the map of instructions.
 *
 * @author florence
 * @author rpless
 */
public class RenderingInstructionBundle implements Iterable<Map.Entry<OutputDeviceName, Collection<RenderingInstruction>>> {
    protected CustomizableMap<OutputDeviceName, Collection<RenderingInstruction>> instructions;

    /**
     * And immutable empty bundle. It cannot be added to.
     */
    public static final RenderingInstructionBundle EMPTY_BUNDLE = new RenderingInstructionBundle(Collections.unmodifiableMap(new HashMap<>()));

    /**
     * Create an empty bundle
     */
    public RenderingInstructionBundle() {
        this(new CustomizableMap<>(new HashMap<>()));
    }

    private RenderingInstructionBundle(Map<OutputDeviceName, Collection<RenderingInstruction>> map) {
        this(new CustomizableMap<>(map));
    }

    /**
     * Create an empty bundle
     */
    private RenderingInstructionBundle(CustomizableMap<OutputDeviceName, Collection<RenderingInstruction>> map) {
        instructions = map;
    }

    /**
     * This is an internal method. Only call when implementing an OutputSystem.
     * Its signature is subject to change.
     * @return the instructions this bundle has collected
     */
    public CustomizableMap<OutputDeviceName, Collection<RenderingInstruction>> getInstructions() {
        return instructions;
    }

    /**
     * EFFECT: add {@code instructions} to the bundle bound to {@code name}
     * return this.
     * @param name Name of the {@link io.metacake.core.output.system.OutputDevice}
     * @param instruction {@link io.metacake.core.output.RenderingInstruction} to bind
     * @return {@code this}
     */
    public RenderingInstructionBundle add(OutputDeviceName name, RenderingInstruction instruction) {
        ensureMappingExists(name);
        instructions.get(name).add(instruction);
        return this;
    }

    /**
     * EFFECT: Add all of the {@code instructions} to the bundle and bind them to the {@link io.metacake.core.output.OutputDeviceName}.
     * @param name Name of the output device
     * @param instructions The instructions to bind.
     * @return {@code this}
     */
    public RenderingInstructionBundle add(OutputDeviceName name, RenderingInstruction... instructions) {
        ensureMappingExists(name);
        this.instructions.get(name).addAll(Arrays.asList(instructions));
        return this;
    }

    /**
     * EFFECT: ensure that there is a mapping between the given device name and at least an empty list.
     * @param name The {@link io.metacake.core.output.OutputDeviceName} to check
     */
    private void ensureMappingExists(OutputDeviceName name) {
        if (!instructions.containsKey(name)) {
            instructions.put(name, new ArrayList<>());
        }
    }

    @Override
    public Iterator<Map.Entry<OutputDeviceName, Collection<RenderingInstruction>>> iterator() {
        return this.getInstructions().iterator();
    }
}