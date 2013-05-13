package io.metacake.core;

import io.metacake.core.common.window.CakeWindow;
import io.metacake.core.input.InputDeviceName;
import io.metacake.core.input.InputSystem;
import io.metacake.core.input.system.InputDevice;
import io.metacake.core.output.OutputDeviceName;
import io.metacake.core.output.OutputSystem;
import io.metacake.core.output.system.OutputDevice;
import io.metacake.core.process.GameRunner;
import io.metacake.core.process.state.GameState;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author florence
 * @author rpless
 */
public class BootstrapperTest {
    CakeWindow window;
    Map<InputDeviceName,InputDevice> inputDevices;
    Map<OutputDeviceName,OutputDevice> outputDevices;
    GameState state;
    Bootstrapper bootstrapper;

    @Before
    public void setup(){
        window = mock(CakeWindow.class);
        inputDevices = new HashMap<>();
        outputDevices = new HashMap<>();
        state = mock(GameState.class);
        bootstrapper = spy(new Bootstrapper(window,inputDevices,outputDevices,state));
    }

    @Test
    public void testBoostrapEventSequence(){
        InputDevice i = mock(InputDevice.class);
        InputDeviceName in = new InputDeviceName();
        OutputDevice o = mock(OutputDevice.class);
        OutputDeviceName on = new OutputDeviceName();
        inputDevices.put(in,i);
        outputDevices.put(on,o);
        bootstrapper = spy(new Bootstrapper(window,inputDevices,outputDevices,state));

        InOrder order = inOrder(i,o,bootstrapper);
        InputSystem is = mock(InputSystem.class);
        OutputSystem os = mock(OutputSystem.class);

        when(bootstrapper.bootstrapInputSystem()).thenReturn(is);
        when(bootstrapper.bootstrapOutputSystem()).thenReturn(os);

        GameRunner r = bootstrapper.bootstrapSystem();

        order.verify(i).bind(eq(window));
        order.verify(o).bind(eq(window));
        order.verify(bootstrapper).bootstrapInputSystem();
        order.verify(bootstrapper).bootstrapOutputSystem();
        order.verify(bootstrapper).bootstrapProcessLayer(eq(is),eq(os));

        verify(is).startInputLoops();
        verify(os).startOutputLoops();
    }
}
