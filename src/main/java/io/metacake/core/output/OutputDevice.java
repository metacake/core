package io.metacake.core.output;

import io.metacake.core.common.window.CakeWindow;

import java.util.List;

/**
 * Interface into any object that will output the state
 * @author florence
 * @author rpless
 */
public interface OutputDevice {
    /**
     * run all instructions
     * @param r the list of instructions to render.
     *          <p>These will be the instructions returned by Renderable#renderingInstructions</p>
     */
    public void render(List<RenderingInstruction> r);

    /**
     * Start any loops needed for this device to run. This method should launch its own threads.
     */
    public void startOutputLoop();

    /**
     * Bind this device to the given window.
     * @param w The window for the game
     */
    public void bind(CakeWindow w);
}
