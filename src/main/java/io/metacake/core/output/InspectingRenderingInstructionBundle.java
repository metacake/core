package io.metacake.core.output;

import io.metacake.core.common.CustomizableMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    public CustomizableMap<OutputDeviceName, Collection<RenderingInstruction>> getInstructions() {
        validateInUse();
        inUse = true;
        CustomizableMap<OutputDeviceName, Collection<RenderingInstruction>> instructionMap = super.getInstructions();
        for (Collection<RenderingInstruction> instructions : instructionMap.values()) {
            InspectableInstruction spy = new InspectableInstruction();
            spies.add(spy);
            instructions.add(spy);
        }
        return instructionMap;
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
        return inUse && spies.stream().allMatch(InspectableInstruction::isDone);
    }

    /**
     * Throw an error if this bundle is already in use
     */
    private void validateInUse() {
        if(inUse) {
            throw new IllegalStateException("This RenderingInstructionBundle is already in use and may not be rendered while in use.");
        }
    }
}