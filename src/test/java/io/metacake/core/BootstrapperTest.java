package io.metacake.core;

import io.metacake.core.common.TimedObserverThread;
import io.metacake.core.common.window.CakeWindow;
import io.metacake.core.common.window.CloseObserver;
import io.metacake.core.input.ActionTrigger;
import io.metacake.core.input.InputDeviceName;
import io.metacake.core.input.InputSystem;
import io.metacake.core.input.system.InputDevice;
import io.metacake.core.output.OutputDeviceName;
import io.metacake.core.output.OutputSystem;
import io.metacake.core.output.RenderingInstruction;
import io.metacake.core.output.RenderingInstructionBundle;
import io.metacake.core.output.system.OutputDevice;
import io.metacake.core.process.GameRunner;
import io.metacake.core.process.state.GameState;
import io.metacake.core.process.state.UserState;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertNull;
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

    @Test
    /**
     * This test was designed to test a specific race condition in the shutdown process where the window
     * would be disposed of before the graphics system finished shutting down.
     */
    public void testShutdownOccursSequentiallyAndSyncronouslyManyTimes() throws InterruptedException {
        for(int i = 0; i < 100; i++){
            testShutdownOccursSequentiallyAndSyncronously();
        }
    }
    public void testShutdownOccursSequentiallyAndSyncronously() throws InterruptedException {
        CakeWindow<Object> window = new CakeWindow(){
            Object test = new Object();
            @Override
            public void close() {
                test = null;
            }

            @Override
            public int getX() {
                return 0;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public int getY() {
                return 0;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public int getWidth() {
                return 0;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public int getHeight() {
                return 0;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void addCloseObserver(CloseObserver o) { }

            @Override
            public Object getRawWindow() {
                return test;
            }
        };

        InputDevice inputDevice = mock(InputDevice.class);
        OutputDevice outputDevice = new OutputDevice() {
            CakeWindow<Object> w;
            TimedObserverThread t;
            @Override
            public void render(List<RenderingInstruction> r) { }

            @Override
            public void startOutputLoop() {
                t = new TimedObserverThread(new Runnable() {
                    @Override
                    public void run() {
                        w.getRawWindow().toString();
                    }
                });
                t.start();
            }

            @Override
            public void shutdown() {
                t.requestStop();
            }

            @Override
            public void bind(CakeWindow w) {
                this.w = w;
            }
        };

        final GameState g = new UserState() {
            @Override
            public GameState tick() {
                return this;
            }

            @Override
            public RenderingInstructionBundle renderingInstructions() {
                return new RenderingInstructionBundle();
            }
        };

        HashMap<InputDeviceName,InputDevice> inputDevices = new HashMap<>();
        inputDevices.put(new InputDeviceName("input"),inputDevice);
        HashMap<OutputDeviceName,OutputDevice> outputDevices = new HashMap<>();
        outputDevices.put(new OutputDeviceName("output"),outputDevice);
        Bootstrapper b = new Bootstrapper(window,inputDevices,outputDevices,state);
        final GameRunner runner = b.bootstrapSystem();

        Thread t = new Thread(){
            @Override
            public void run(){
                runner.mainLoop(g,5);
            }
        };
        t.start();
        Thread.sleep(1000);
        runner.stop();
        t.join();

        assertNull(window.getRawWindow());
    }
}
