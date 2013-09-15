package io.metacake.core.output;

import io.metacake.core.common.CustomizableMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        public boolean isDone() {
            return isDone;
        }
    }

    private List<InspectableInstruction> spies = new ArrayList<>();
    private boolean inUse = false;

    @Override
    public CustomizableMap<OutputDeviceName, List<RenderingInstruction>> getInstructions() {
        validateInUse();
        CustomizableMap<OutputDeviceName, List<RenderingInstruction>> instMap = super.getInstructions();
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
        return spies.stream().allMatch(InspectableInstruction::isDone) && inUse;
    }

    /**
     * Throw an error if this bundle is already in use
     */
    private void validateInUse() {
        if(inUse) {
            throw new IllegalStateException("Bundle already in use, cannot rerender");
        }
    }
}