package io.metacake.core.process;

import io.metacake.core.common.window.CakeWindow;
import io.metacake.core.input.InputSystem;
import io.metacake.core.output.OutputSystem;
import io.metacake.core.output.RenderingInstructionBundle;
import io.metacake.core.process.state.EndState;
import io.metacake.core.process.state.GameState;
import io.metacake.core.process.state.UserState;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * @author florence
 * @author rpless
 */
public class GameRunnerTest {
    GameState s;
    InputSystem is;
    OutputSystem os;
    CakeWindow w;
    GameRunner system;

    @Before
    public void setup(){
        s = new UserState() {
            @Override
            public GameState tick() {
                return this;
            }

            @Override
            public RenderingInstructionBundle renderingInstructions() {
                return RenderingInstructionBundle.EMPTY_BUNDLE;
            }
        };
        is = mock(InputSystem.class);
        os = mock(OutputSystem.class);
        w = mock(CakeWindow.class);
        system = new GameRunner(is,os,w);
    }

    @Test
    public void stopActuallyStops() throws Exception{
        final int loopTime = 5;
        Thread t = new Thread(){
            @Override
            public void run(){
                system.mainLoop(s,loopTime);
            }
        };

        t.start();

        Thread.sleep(5);
        system.stop();
        t.join();

        verify(is,times(1)).dispose();
        verify(os,times(1)).dispose();
    }

    @Test
    public void closesOnEndState() throws Exception
    {
        runGameWithEndState(EndState.closeWith(s));

        verify(is,times(1)).dispose();
        verify(os,times(1)).dispose();
        verify(w,times(1)).dispose();
    }

    @Test
    public void testClosingWindowOnEndState() throws Exception {
        runGameWithEndState(EndState.endWith(s));

        verify(is,times(1)).dispose();
        verify(os,times(1)).dispose();
        verify(w,times(0)).dispose();

        system.stop();

        verify(w,times(1)).dispose();
    }

    private void runGameWithEndState(final GameState end) throws Exception {
        final int loopTime = 5;//miliseconds
        final GameState state = new UserState() {
            int count = 0;
            @Override
            public GameState tick() {
                count += 1;
                if(count > 1000) {
                    return end;
                } else {
                    return this;
                }
            }

            @Override
            public RenderingInstructionBundle renderingInstructions() {
                return RenderingInstructionBundle.EMPTY_BUNDLE;
            }
        };

        Thread t = new Thread(){
            @Override
            public void run() {
                system.mainLoop(state,loopTime);
            }
        };

        t.run();
        t.join();
    }
}
