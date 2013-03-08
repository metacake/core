package io.metacake.core.output;

import java.util.List;

/**
 * Interface into any object that will output the state
 * @author florence
 * @author rpless
 */
public interface OutputDevice {
    /**
     * run all instructions
     * @param r the list of instructions to render. these will be the instructions returned by Renderable#renderingInstructions
     */
    public void render(List<RenderingInstruction> r);
}
