package io.metacake.core.output;

/**
 * Represents an action to be rendered by some Rendering Context.
 * @author florence
 * @author rpless
 *
 * @param <T> The Render
 */
public interface RenderingInstruction<T> {

    /**
     * Render this instruction.
     * @param context The context for rendering this instruction.
     */
    public void render(T context);
}