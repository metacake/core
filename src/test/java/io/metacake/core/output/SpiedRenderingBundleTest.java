package io.metacake.core.output;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class SpiedRenderingBundleTest {
    private final OutputDeviceName testOutputDeviceName = new OutputDeviceName("test");
    private final RenderingInstruction mockInstruction = mock(RenderingInstruction.class);

    @Test
    public void emptyBundleIsDone() {
        SpiedRenderingBundle bundle = new SpiedRenderingBundle();
        assertTrue(bundle.isDone());
    }

    @Test
    public void nonemptyBundleIsNotDone() {
        SpiedRenderingBundle bundle = new SpiedRenderingBundle();
        bundle.add(testOutputDeviceName, mockInstruction);
        assertFalse(bundle.isDone());
    }
}