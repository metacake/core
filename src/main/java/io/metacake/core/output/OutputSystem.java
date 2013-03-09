package io.metacake.core.output;

/**
 * The interface into the output system as far as the processing system is concerned
 * @author florence
 * @author rpless
 */
public interface OutputSystem {
    /**
     * Add this renderable t the queue of things to render. It will be immediatly translated to something immutable via
     * Renderable#renderingInstructions
     * @param r the current renderable
     */
    public void addToRenderQueue(Renderable r);
}
