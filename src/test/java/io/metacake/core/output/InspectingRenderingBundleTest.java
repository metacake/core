package io.metacake.core.output;

import io.metacake.core.common.window.CakeWindow;
import io.metacake.core.output.system.OutputDevice;
import io.metacake.core.output.system.OutputLayer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class InspectingRenderingBundleTest {
    private final OutputDeviceName testOutputDeviceName = new OutputDeviceName("test");
    @Mock
    private final RenderingInstruction mockInstruction = mock(RenderingInstruction.class);
    private InspectingRenderingInstructionBundle bundle;

    @Before
    public void setup() {
        bundle = new InspectingRenderingInstructionBundle();
    }


    @Test
    public void testDoneOnFullBundle() {
        bundle.add(testOutputDeviceName, mockInstruction);

        runInstructionsOnNull(bundle);

        assertTrue(bundle.isDone());
        bundle.reset();
        assertFalse(bundle.isDone());
    }

    @Test
    public void emptyBundleIsDone() {
        assertFalse(bundle.isDone());
        bundle.getInstructions();
        assertTrue(bundle.isDone());
    }


    @Test
    public void nonemptyBundleIsNotDone() {
        bundle.add(testOutputDeviceName, mockInstruction);
        assertFalse(bundle.isDone());
    }


    @Test
    public void testCannotResuseWithoutReset(){
        bundle.getInstructions();
        try {
            bundle.getInstructions();
        }
        catch (IllegalStateException e) {
            return;
        }
        fail();
    }

    @Test
    public void testCanReuseWithReset() {
        bundle.getInstructions();
        bundle.reset();
        bundle.getInstructions();
    }

    private void runInstructionsOnNull(RenderingInstructionBundle bundle) {
        for(Map.Entry<OutputDeviceName,List<RenderingInstruction>> e : bundle.getInstructions().entrySet()) {
            for(RenderingInstruction r : e.getValue()) {
                r.render(null);
            }
        }
    }
}