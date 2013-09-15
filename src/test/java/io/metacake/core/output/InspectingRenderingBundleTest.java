package io.metacake.core.output;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class InspectingRenderingBundleTest {
    private final OutputDeviceName testOutputDeviceName = new OutputDeviceName("test");
    private final RenderingInstruction mockInstruction = mock(RenderingInstruction.class);

    @Test
    public void emptyBundleIsDone() {
        InspectingRenderingInstructionBundle bundle = new InspectingRenderingInstructionBundle();
        assertTrue(bundle.isDone());
    }

    @Test
    public void nonemptyBundleIsNotDone() {
        InspectingRenderingInstructionBundle bundle = new InspectingRenderingInstructionBundle();
        bundle.add(testOutputDeviceName, mockInstruction);
        assertFalse(bundle.isDone());
    }
}