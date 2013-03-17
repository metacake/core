package io.metacake.engine;

import io.metacake.core.output.RenderingInstruction;
import io.metacake.core.process.GameState;

import java.util.Collections;
import java.util.List;

/**
 * @author florence
 * @author rpless
 */
public class SyncState {
    private List<RenderingInstruction> s = Collections.emptyList();
    public synchronized void setState(List<RenderingInstruction> s) {
        this.s = s;
    }
    public synchronized List<RenderingInstruction> getState(){
        return s;
    }
}
