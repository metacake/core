package io.metacake.core.output;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author florence
 * @author rpless
 */
public class RenderingInstructionBundleTest {
    @Test
    public void canAddAndRemove(){
        RenderingInstructionBundle bundle = new RenderingInstructionBundle();

        assertTrue(bundle.getInstructions().isEmpty());

        OutputDeviceName o = new OutputDeviceName("test");
        RenderingInstruction r = new RenderingInstruction() {};

        RenderingInstructionBundle next = bundle.add(o,r);

        assertEquals(r,bundle.getInstructions().get(o).get(0));
    }
}
