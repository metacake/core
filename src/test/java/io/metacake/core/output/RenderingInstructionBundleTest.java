package io.metacake.core.output;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author florence
 * @author rpless
 */
public class RenderingInstructionBundleTest {

    private final OutputDeviceName testOutputDeviceName = new OutputDeviceName("test");
    private final RenderingInstruction one = new RenderingInstruction() {
        public void render(Object context) {}
    };
    private final RenderingInstruction two = new RenderingInstruction() {
        public void render(Object context) {}
    };
    private final RenderingInstruction three = new RenderingInstruction() {
        public void render(Object context) {}
    };

    @Test
    public void bundleStartsEmpty() {
        RenderingInstructionBundle bundle = new RenderingInstructionBundle();
        assertTrue(bundle.getInstructions().isEmpty());
    }

    @Test
    public void canAddAndRemove() {
        RenderingInstructionBundle bundle = new RenderingInstructionBundle();
        bundle.add(testOutputDeviceName, one);

        assertEquals(1, bundle.getInstructions().size());
        assertEquals(1, bundle.getInstructions().get(testOutputDeviceName).size());
        assertEquals(one, bundle.getInstructions().get(testOutputDeviceName).get(0));
    }

    @Test
    public void canAddMultipleInstructions() {
        RenderingInstructionBundle bundle = new RenderingInstructionBundle();
        bundle.add(testOutputDeviceName, one, two, three);
        assertEquals(1, bundle.getInstructions().size());
        assertEquals(3, bundle.getInstructions().get(testOutputDeviceName).size());
        assertEquals(one, bundle.getInstructions().get(testOutputDeviceName).get(0));
        assertEquals(two, bundle.getInstructions().get(testOutputDeviceName).get(1));
        assertEquals(three, bundle.getInstructions().get(testOutputDeviceName).get(2));
    }
}