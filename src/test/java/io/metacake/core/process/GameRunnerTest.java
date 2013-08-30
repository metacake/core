package io.metacake.core.process;

import io.metacake.core.common.window.CakeWindow;
import io.metacake.core.input.InputSystem;
import io.metacake.core.output.OutputSystem;
import io.metacake.core.output.RenderingInstructionBundle;
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
                return new RenderingInstructionBundle();
            }
        };
        is = mock(InputSystem.class);
        os = mock(OutputSystem.class);

        system = new GameRunner(is,os,mock(CakeWindow.class));
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
        t.join(5);

        verify(is,times(1)).dispose();
        verify(os,times(1)).dispose();
    }
}
