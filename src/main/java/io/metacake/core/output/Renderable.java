package io.metacake.core.output;

/**
 * A {@code Renderable} is an object that is capable of delivering a bundle of objects that can rendered by
 * part of the {@link io.metacake.core.output.OutputSystem}.
 * @author florence
 * @author rpless
 */
public interface Renderable {
    /**
     * @return Returns a map of {@link io.metacake.core.output.system.OutputDevice} mapped to the instructions they must render.
     */
    public RenderingInstructionBundle renderingInstructions();
}