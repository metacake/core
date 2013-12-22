package io.metacake.core.output;

import io.metacake.core.common.Disposable;

/**
 * The Process Layer's Interface into the Output System.
 * @author florence
 * @author rpless
 */
public interface OutputSystem extends Disposable {
    /**
     * Add this {@link io.metacake.core.output.Renderable} t the queue of things to render. It will be immediately
     * translated to something immutable via {@link io.metacake.core.output.Renderable#renderingInstructions}.
     * @param r the current renderable
     */
    public void addToRenderQueue(Renderable r);

    /**
     * By the time this method return all output devices must have started their main threads.
     */
    public void startOutputLoops();
}