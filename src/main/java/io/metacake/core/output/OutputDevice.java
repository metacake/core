package io.metacake.core.output;

import io.metacake.core.common.window.CakeWindow;

import java.util.List;

/**
 * An OutputDevice is an interface into any kind of device that produces output. These devices receive renderables and
 * render these as output.
 * @author florence
 * @author rpless
 */
public interface OutputDevice {
    /**
     * Render all of the given RenderingInstructions.
     * @param r the list of instructions to render.
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
