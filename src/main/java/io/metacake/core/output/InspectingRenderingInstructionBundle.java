package io.metacake.core.output;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A InspectingRenderingInstructionBundle inserts instructions into the instruction set
 * in order to determine when all instructions have been rendered.
 *
 * @author rpless
 */
public class InspectingRenderingInstructionBundle extends RenderingInstructionBundle {

    /**
     *  An instruction that keeps track of when it is rendered.
     */
    private class InspectableInstruction implements RenderingInstruction {
        private boolean isDone = false;
        @Override
        public void render(Object context) {
            isDone = true;
        }
    }

    private List<InspectableInstruction> spies = new ArrayList<>();
    private boolean inUse = false;

    @Override
    public Map<OutputDeviceName, List<RenderingInstruction>> getInstructions() {
        Map<OutputDeviceName, List<RenderingInstruction>> instMap = super.getInstructions();
        for (List<RenderingInstruction> instructions : instMap.values()) {
            InspectableInstruction spy = new InspectableInstruction();
            spies.add(spy);
            instructions.add(spy);
        }
        inUse = true;
        return instMap;
    }

    /**
     *  Reset this bundle
     */
    public void reset() {
        inUse = false;
        spies.clear();
    }

    /**
     * @return Have all of the RenderingInstructions in this bundle been rendered.
     */
    public boolean isDone() {
        boolean isDone = inUse || instructions.isEmpty();
        for (InspectableInstruction spy : spies) {
            isDone = isDone && spy.isDone;
        }
        return isDone;
    }
}