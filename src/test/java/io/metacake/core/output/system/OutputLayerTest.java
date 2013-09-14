package io.metacake.core.output.system;

import io.metacake.core.output.OutputDeviceName;
import io.metacake.core.output.Renderable;
import io.metacake.core.output.RenderingInstruction;
import io.metacake.core.output.RenderingInstructionBundle;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author florence
 * @author rpless
 */
public class OutputLayerTest {

    OutputDeviceName n1 = new OutputDeviceName();
    OutputDeviceName n2 = new OutputDeviceName();
    OutputDevice o1;
    OutputDevice o2;
    OutputLayer system;

    @Before
    public void setup(){
        o1 = mock(OutputDevice.class);
        o2 = mock(OutputDevice.class);
        Map<OutputDeviceName,OutputDevice> devices = new HashMap<>();
        devices.put(n1,o1);
        devices.put(n2,o2);
        system = new OutputLayer(devices);
    }

    @Test
    public void startOutputLoopsTouchesAllDevicesOnce(){
        system.startOutputLoops();
        verify(o1,times(1)).startOutputLoop();
        verify(o2,times(1)).startOutputLoop();
    }

    @Test
    public void addToRenderQueuePassesProperValues(){
        RenderingInstructionBundle bundle = new RenderingInstructionBundle();
        bundle.add(n1, mock(RenderingInstruction.class));
        bundle.add(n1, mock(RenderingInstruction.class));

        Renderable r = mock(Renderable.class);

        when(r.renderingInstructions()).thenReturn(bundle);

        system.addToRenderQueue(r);

        verify(o1).render(eq(bundle.getInstructions().get(n1)));
        verify(o2,never()).render(any(List.class));
    }
}
